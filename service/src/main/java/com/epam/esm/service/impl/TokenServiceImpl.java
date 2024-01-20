package com.epam.esm.service.impl;

import com.epam.esm.dto.TokenDTO;
import com.epam.esm.repository.TokenRepository;
import com.epam.esm.service.TokenService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Pagination;
import com.epam.esm.util.mapper.TokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the {@link TokenService} interface.
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    /**
     * Updates expiring tokens at fixed rate.
     */
    @Scheduled(fixedRate = 1000)
    public void updateExpiringTokens() {
        LocalDateTime fifteenMinutesBeforeNow = LocalDateTime.now().minusMinutes(15);

        tokenRepository.findByCreatedAtBefore(fifteenMinutesBeforeNow)
                .forEach(token -> {
                    token.setExpired(true);
                    tokenRepository.save(token);
                });
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<TokenDTO> findAllByPage(int page, int size) {
        Pagination pagination = new Pagination(page, size);
        return tokenRepository.findAllByPage(pagination).
                stream().map(tokenMapper::toTokenDTO).toList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public TokenDTO findById(Long id) {
        return tokenMapper.toTokenDTO(tokenRepository.findById(id));
    }

}
