package com.aca.coc.clans.service;

import static com.aca.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.aca.coc.clans.common.AcademeClan;
import com.aca.coc.clans.common.ExceptionCode;
import com.aca.coc.clans.common.exception.CustomRuntimeException;
import com.aca.coc.clans.common.exception.handler.ExceptionHandler;
import com.aca.coc.clans.db.entity.clan.ClanAssignedPlayerEntity;
import com.aca.coc.clans.db.entity.clan.ClanAssignedPlayerPKEntity;
import com.aca.coc.clans.db.entity.clan.ClanBadgeEntity;
import com.aca.coc.clans.db.entity.clan.ClanContentEntity;
import com.aca.coc.clans.db.entity.clan.ClanEntity;
import com.aca.coc.clans.db.entity.clan.ClanLeagueAssignedPlayerEntity;
import com.aca.coc.clans.db.entity.common.YnType;
import com.aca.coc.clans.db.entity.common.converter.IconUrlEntityConverter;
import com.aca.coc.clans.db.entity.player.PlayerEntity;
import com.aca.coc.clans.db.repository.clan.ClanAssignedPlayerRepository;
import com.aca.coc.clans.db.repository.clan.ClanContentRepository;
import com.aca.coc.clans.db.repository.clan.ClanLeagueAssignedPlayerRepository;
import com.aca.coc.clans.db.repository.clan.ClanRepository;
import com.aca.coc.clans.db.repository.player.PlayerRepository;
import com.aca.coc.clans.domain.clans.ClanAssignedMemberListResponse;
import com.aca.coc.clans.domain.clans.ClanAssignedPlayer;
import com.aca.coc.clans.domain.clans.ClanAssignedPlayerBulk;
import com.aca.coc.clans.domain.clans.ClanContent;
import com.aca.coc.clans.domain.clans.ClanCurrentWarRes;
import com.aca.coc.clans.domain.clans.ClanMemberListRes;
import com.aca.coc.clans.domain.clans.ClanRequest;
import com.aca.coc.clans.domain.clans.ClanResponse;
import com.aca.coc.clans.domain.clans.LeagueClanRes;
import com.aca.coc.clans.domain.clans.converter.ClanCurrentWarResConverter;
import com.aca.coc.clans.domain.clans.converter.ClanMemberListResConverter;
import com.aca.coc.clans.domain.clans.converter.ClanResponseConverter;
import com.aca.coc.clans.domain.players.PlayerResponse;
import com.aca.coc.clans.domain.players.converter.PlayerResponseConverter;
import com.aca.coc.external.coc.clan.ClanApiService;
import com.aca.coc.external.coc.clan.domain.clan.Clan;
import com.aca.coc.external.coc.clan.domain.clan.ClanMemberList;
import com.aca.coc.external.coc.clan.domain.clan.ClanWar;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClansService {

    private final ClanApiService clanApiService;

    private final ClanRepository clanRepository;
    private final ClanContentRepository clanContentRepository;
    private final ClanAssignedPlayerRepository clanAssignedPlayerRepository;
    private final ClanLeagueAssignedPlayerRepository clanLeagueAssignedPlayerRepository;

    private final ClanResponseConverter clanResponseConverter;
    private final IconUrlEntityConverter iconUrlEntityConverter;
    private final ClanCurrentWarResConverter clanCurrentWarResConverter;
    private final ClanMemberListResConverter clanMemberListResConverter;

    private final PlayerRepository playerRepository;
    private final PlayersService playersService;
    private final PlayerResponseConverter playerResponseConverter;

    public ClanResponse findClanByClanTag(String clanTag) {
        Clan clan = clanApiService.findClanByClanTag(clanTag)
                                  .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜 조회 실패"));

        return clanResponseConverter.convert(clan);
    }

    public ClanCurrentWarRes getClanCurrentWar(String clanTag) {
        ClanWar clanCurrentWar = clanApiService.findClanCurrentWarByClanTag(clanTag)
                                               .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "현재 클랜 전쟁 조회 실패"));

        return clanCurrentWarResConverter.convert(clanCurrentWar);
    }


    public ClanCurrentWarRes getLeagueWar(String clanTag, String roundTag) {
        ClanWar leagueWar = clanApiService.findLeagueWarByRoundTag(roundTag)
                                          .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "리그전 조회 실패"));

        leagueWar.swapWarClan(clanTag);
        return clanCurrentWarResConverter.convert(leagueWar);
    }

    public List<ClanResponse> getClanList() {
        List<ClanEntity> clans = clanRepository.findAll();

        return clans.stream()
                    .map(clanResponseConverter::convert)
                    .sorted(Comparator.comparing(ClanResponse::getOrder))
                    .collect(Collectors.toList());
    }

    public List<ClanResponse> getClanWarResList() {
        List<ClanEntity> clanWarList = clanRepository.findClanWarList();

        return clanWarList.stream()
                          .map(clanResponseConverter::convert)
                          .collect(Collectors.toList());
    }

    public List<ClanResponse> getClanWarParallelResList() {
        List<ClanEntity> clanWarParallelList = clanRepository.findClanWarParallelList();

        return clanWarParallelList.stream()
                                  .map(clanResponseConverter::convert)
                                  .collect(Collectors.toList());
    }

    public List<ClanResponse> getClanCaptialList() {
        List<ClanEntity> clanCapitalList = clanRepository.findClanCapitalList();

        return clanCapitalList.stream()
                              .map(clanResponseConverter::convert)
                              .collect(Collectors.toList());
    }


    public LeagueClanRes getLeagueClan(String clanTag) throws IOException {
        AcademeClan clan = AcademeClan.findByTag(clanTag);
        return LeagueClanRes.create(clan);
    }


    public ClanMemberListRes findClanMembersByClanTag(String clanTag) {
        ClanMemberList clanMemberList = clanApiService.findClanMembersByClanTag(clanTag)
                                                      .orElseThrow(() -> CustomRuntimeException.create(ExceptionCode.EXTERNAL_ERROR, "클랜 사용자 조회 실패"));

        ClanMemberListRes clanMemberListRes = clanMemberListResConverter.convert(clanMemberList);
        clanMemberListRes.setClanTag(clanTag);
        return clanMemberListRes;
    }

    public List<ClanMemberListRes> findClanMembersByClanTags(List<String> clanTags) {
        return clanTags.stream()
                       .parallel()
                       .map(this::findClanMembersByClanTag)
                       .collect(Collectors.toList());
    }

    public List<ClanResponse> findClanByClanTags(List<String> clanTags) {
        return clanTags.stream()
                       .parallel()
                       .map(clanApiService::findClanByClanTag)
                       .filter(Optional::isPresent)
                       .map(clan -> clanResponseConverter.convert(clan.get()))
                       .collect(Collectors.toList());
    }

    @Transactional
    public void updateClanContentStatus(ClanContent request) {

        ClanContentEntity clanContent = clanContentRepository.findById(request.getTag())
                                                             .orElseThrow(() -> createNotFoundException("클랜[%s] 컨텐츠 정보".formatted(request.getTag())));

        if (StringUtils.hasText(request.getClanWarYn())) {
            clanContent.setClanWarYn(request.getClanWarYn());
        }

        if (StringUtils.hasText(request.getClanWarLeagueYn())) {
            clanContent.setWarLeagueYn(request.getClanWarLeagueYn());
        }

        if (StringUtils.hasText(request.getClanCapitalYn())) {
            clanContent.setClanCapitalYn(request.getClanCapitalYn());
        }

        if (StringUtils.hasText(request.getClanWarParallelYn())) {
            clanContent.setClanWarParallelYn(request.getClanWarParallelYn());
        }

        clanContentRepository.save(clanContent);
    }

    @Transactional
    public void deleteClan(String clanTag) {
        ClanEntity clan = clanRepository.findById(clanTag)
                                        .orElse(null);

        if (ObjectUtils.isEmpty(clan)) {
            // 클랜 없는 경우 성공
            return;
        }

        clan.setVisibleYn(YnType.N);
        clanRepository.save(clan);
    }

    @Transactional
    public ClanResponse registerClan(ClanRequest clanRequest) {
        clanRequest.validate();

        Optional<ClanEntity> clanEntity = clanRepository.findById(clanRequest.getTag());
        if (clanEntity.isPresent()) {
            ClanEntity updateClanEntity = clanEntity.get();
            updateClanEntity.setVisibleYn(YnType.Y);
            ClanEntity resultClan = clanRepository.save(updateClanEntity);
            return clanResponseConverter.convert(resultClan);
        }

        Integer clanMaxOrders = clanRepository.selectMaxOrders();
        if (Objects.isNull(clanMaxOrders)) {
            clanMaxOrders = 0;
        }

        ClanEntity createClan = createClanEntity(clanRequest, clanMaxOrders);
        createClan.changeClanContent(ClanContentEntity.empty(clanRequest.getTag()));
        createClan.changeBadgeUrl(ClanBadgeEntity.builder()
                                                 .tag(clanRequest.getTag())
                                                 .iconUrl(iconUrlEntityConverter.convert(clanRequest.getBadgeUrl()))
                                                 .build());
        clanRepository.save(createClan);

        return clanResponseConverter.convert(createClan);
    }

    private ClanEntity createClanEntity(ClanRequest clanRequest, Integer clanMaxOrders) {
        return ClanEntity.builder()
                         .tag(clanRequest.getTag())
                         .name(clanRequest.getName())
                         .order(clanMaxOrders + 1)
                         .visibleYn(YnType.Y)
                         .regDate(LocalDateTime.now())
                         .build();
    }

    public ClanAssignedMemberListResponse findClanAssignedMembers(String clanTag) {
        String latestSeasonDate = clanAssignedPlayerRepository.findLatestSeasonDate();
        if (ObjectUtils.isEmpty(latestSeasonDate)) {
            latestSeasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        List<ClanAssignedPlayerEntity> clanAssignedPlayers = clanAssignedPlayerRepository.findClanAssignedPlayersByClanTagAndSeasonDate(clanTag, latestSeasonDate);

        List<String> playerTags = clanAssignedPlayers.stream()
                                                      .map(ClanAssignedPlayerEntity::getPlayerTag)
                                                      .toList();
        List<PlayerResponse> players = playersService.findPlayerBy(playerTags);

        return ClanAssignedMemberListResponse.create(clanTag, latestSeasonDate, players);
    }

    @Transactional
    public void postClanAssignedMember(String clanTag, String seasonDate, String playerTag) {

        Optional<PlayerEntity> findPlayer = playerRepository.findById(playerTag);
        if (findPlayer.isEmpty()) {
            // 배정 시 등록되지 않은 클랜원은 등록 처리
            playersService.registerPlayer(playerTag);
        }

        ClanAssignedPlayerPKEntity clanAssignedPlayerPK = ClanAssignedPlayerPKEntity.builder()
                                                                                    .seasonDate(seasonDate)
                                                                                    .playerTag(playerTag)
                                                                                    .build();

        Optional<ClanAssignedPlayerEntity> findClanAssignedPlayer = clanAssignedPlayerRepository.findById(clanAssignedPlayerPK);
        if (findClanAssignedPlayer.isPresent()) {
            ClanAssignedPlayerEntity clanAssignedPlayerEntity = findClanAssignedPlayer.get();
            if (Objects.equals(clanTag, clanAssignedPlayerEntity.getClan().getTag())) {
                // 이미 배정
                return;
            }

            ClanEntity clanEntity = clanRepository.findById(clanAssignedPlayerEntity.getClan().getTag())
                                                  .orElseThrow(() -> ExceptionHandler.createBadRequestException(ExceptionCode.ALREADY_DATA,
                                                                                                                clanAssignedPlayerEntity.getClan().getName() + "에 배정된 상태"));

            throw ExceptionHandler.createBadRequestException(ExceptionCode.ALREADY_DATA.getCode(), "[%s] 배정된 상태".formatted(clanEntity.getName()));
        }

        ClanEntity clan = clanRepository.findById(clanTag)
                                        .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 실패".formatted(clanTag)));
        ClanAssignedPlayerEntity clanAssignedPlayer = ClanAssignedPlayerEntity.builder()
                                                                              .id(clanAssignedPlayerPK)
                                                                              .clan(clan)
                                                                              .build();

        clanAssignedPlayerRepository.save(clanAssignedPlayer);
    }

    @Transactional
    public void deleteClanAssignedMember(String clanTag, String seasonDate, String playerTag) {
        ClanAssignedPlayerPKEntity clanAssignedPlayerPK = ClanAssignedPlayerPKEntity.builder()
                                                                                    .seasonDate(seasonDate)
                                                                                    .playerTag(playerTag)
                                                                                    .build();

        clanAssignedPlayerRepository.deleteById(clanAssignedPlayerPK);
    }

    public ClanAssignedMemberListResponse getLatestClanAssignedMembers() {

        String latestSeasonDate = clanAssignedPlayerRepository.findLatestSeasonDate();
        if (ObjectUtils.isEmpty(latestSeasonDate)) {
            latestSeasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        List<ClanAssignedPlayerEntity> clanAssignedPlayers = clanAssignedPlayerRepository.findBySeasonDate(latestSeasonDate);

        List<PlayerResponse> players = clanAssignedPlayers.stream()
                                                          .map(playerResponseConverter::convert)
                                                          .collect(Collectors.toList());

        return ClanAssignedMemberListResponse.create(Strings.EMPTY, latestSeasonDate, players);
    }

    @Transactional
    public void registerClanAssignedMembers(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        List<ClanAssignedPlayerEntity> clanAssignedPlayers = makeClanAssignedPlayerEntities(clanAssignedPlayerBulk);

        // 배정 일괄 삭제
        clanAssignedPlayerRepository.deleteAllBySeasonDate(clanAssignedPlayerBulk.getSeasonDate());

        // 배정 일괄 등록
        clanAssignedPlayerRepository.saveAll(clanAssignedPlayers);
    }

    private List<ClanAssignedPlayerEntity> makeClanAssignedPlayerEntities(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        return clanAssignedPlayerBulk.getPlayers()
                                     .stream()
                                     .map(player -> makeClanAssignedPlayerEntity(clanAssignedPlayerBulk.getSeasonDate(), player))
                                     .collect(Collectors.toList());
    }

    private ClanAssignedPlayerEntity makeClanAssignedPlayerEntity(String seasonDate, ClanAssignedPlayer player) {
        ClanEntity clan = clanRepository.findById(player.getClanTag())
                                        .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 실패".formatted(player.getClanTag())));

        return ClanAssignedPlayerEntity.builder()
                                       .id(ClanAssignedPlayerPKEntity.builder()
                                                                     .seasonDate(seasonDate)
                                                                     .playerTag(player.getPlayerTag())
                                                                     .build())
                                       .clan(clan)
                                       .build();
    }

    public ClanAssignedMemberListResponse getLatestLeagueAssignedMembers() {

        String latestSeasonDate = clanLeagueAssignedPlayerRepository.findLatestLeagueSeasonDate();
        if (ObjectUtils.isEmpty(latestSeasonDate)) {
            latestSeasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        List<ClanLeagueAssignedPlayerEntity> clanAssignedPlayers = clanLeagueAssignedPlayerRepository.findBySeasonDate(latestSeasonDate);

        List<PlayerResponse> players = clanAssignedPlayers.stream()
                                                          .map(playerResponseConverter::convert)
                                                          .collect(Collectors.toList());

        return ClanAssignedMemberListResponse.create(Strings.EMPTY, latestSeasonDate, players);
    }

    public ClanAssignedMemberListResponse findClanLeagueAssignedMembers(String clanTag) {
        String latestLeagueSeasonDate = clanLeagueAssignedPlayerRepository.findLatestLeagueSeasonDate();
        if (ObjectUtils.isEmpty(latestLeagueSeasonDate)) {
            latestLeagueSeasonDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        List<ClanLeagueAssignedPlayerEntity> clanLeagueAssignedPlayers = clanLeagueAssignedPlayerRepository.findClanLeagueAssignedPlayersByClanTagAndSeasonDate(clanTag, latestLeagueSeasonDate);

        List<String> playerTags = clanLeagueAssignedPlayers.stream()
                                                           .map(ClanLeagueAssignedPlayerEntity::getPlayerTag)
                                                           .toList();
        List<PlayerResponse> players = playersService.findPlayerBy(playerTags);

        return ClanAssignedMemberListResponse.create(clanTag, latestLeagueSeasonDate, players);
    }

    @Transactional
    public void postLeagueAssignedMember(String clanTag, String seasonDate, String playerTag) {

        Optional<PlayerEntity> findPlayer = playerRepository.findById(playerTag);
        if (findPlayer.isEmpty()) {
            // 배정 시 등록되지 않은 클랜원은 등록 처리
            playersService.registerPlayer(playerTag);
        }

        ClanAssignedPlayerPKEntity clanAssignedPlayerPK = ClanAssignedPlayerPKEntity.builder()
                                                                                    .seasonDate(seasonDate)
                                                                                    .playerTag(playerTag)
                                                                                    .build();

        Optional<ClanLeagueAssignedPlayerEntity> findClanLeagueAssignedPlayer = clanLeagueAssignedPlayerRepository.findById(clanAssignedPlayerPK);
        if (findClanLeagueAssignedPlayer.isPresent()) {
            ClanLeagueAssignedPlayerEntity clanLeagueAssignedPlayer = findClanLeagueAssignedPlayer.get();
            if (Objects.equals(clanTag, clanLeagueAssignedPlayer.getClan().getTag())) {
                // 이미 배정
                return;
            }

            ClanEntity clanEntity = clanRepository.findById(clanLeagueAssignedPlayer.getClan().getTag())
                                                  .orElseThrow(() -> ExceptionHandler.createBadRequestException(ExceptionCode.ALREADY_DATA,
                                                                                                                clanLeagueAssignedPlayer.getClan().getName() + "에 배정된 상태"));

            throw ExceptionHandler.createBadRequestException(ExceptionCode.ALREADY_DATA.getCode(), "[%s] 리그 배정된 상태".formatted(clanEntity.getName()));
        }

        ClanEntity clan = clanRepository.findById(clanTag)
                                        .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 실패".formatted(clanTag)));
        ClanLeagueAssignedPlayerEntity clanLeagueAssignedPlayer = ClanLeagueAssignedPlayerEntity.builder()
                                                                                    .id(clanAssignedPlayerPK)
                                                                                    .clan(clan)
                                                                                    .build();

        clanLeagueAssignedPlayerRepository.save(clanLeagueAssignedPlayer);
    }

    @Transactional
    public void deleteClanLeagueAssignedMember(String clanTag, String seasonDate, String playerTag) {
        ClanAssignedPlayerPKEntity clanAssignedPlayerPK = ClanAssignedPlayerPKEntity.builder()
                                                                                    .seasonDate(seasonDate)
                                                                                    .playerTag(playerTag)
                                                                                    .build();

        clanLeagueAssignedPlayerRepository.deleteById(clanAssignedPlayerPK);
    }

    @Transactional
    public void registerClanLeagueAssignedMembers(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        List<ClanLeagueAssignedPlayerEntity> clanAssignedPlayers = makeClanLeagueAssignedPlayerEntities(clanAssignedPlayerBulk);

        // 배정 일괄 삭제
        clanLeagueAssignedPlayerRepository.deleteAllBySeasonDate(clanAssignedPlayerBulk.getSeasonDate());

        // 배정 일괄 등록
        clanLeagueAssignedPlayerRepository.saveAll(clanAssignedPlayers);
    }

    private List<ClanLeagueAssignedPlayerEntity> makeClanLeagueAssignedPlayerEntities(ClanAssignedPlayerBulk clanAssignedPlayerBulk) {
        return clanAssignedPlayerBulk.getPlayers()
                                     .stream()
                                     .map(player -> makeClanLeagueAssignedPlayerEntity(clanAssignedPlayerBulk.getSeasonDate(), player))
                                     .collect(Collectors.toList());
    }

    private ClanLeagueAssignedPlayerEntity makeClanLeagueAssignedPlayerEntity(String seasonDate, ClanAssignedPlayer player) {
        ClanEntity clan = clanRepository.findById(player.getClanTag())
                                        .orElseThrow(() -> createNotFoundException("클랜(%s) 조회 실패".formatted(player.getClanTag())));

        return ClanLeagueAssignedPlayerEntity.builder()
                                             .id(ClanAssignedPlayerPKEntity.builder()
                                                                           .seasonDate(seasonDate)
                                                                           .playerTag(player.getPlayerTag())
                                                                           .build())
                                             .clan(clan)
                                             .build();
    }

    public Optional<ClanEntity> findClanEntityBy(String clanTag) {
        return clanRepository.findById(clanTag);
    }
}
