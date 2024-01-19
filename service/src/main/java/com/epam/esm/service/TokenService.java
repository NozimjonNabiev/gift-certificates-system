package com.epam.esm.service;

import com.epam.esm.dto.TokenDTO;


import java.util.List;

public interface TokenService {

    List<TokenDTO> findAllByPage(int page, int size);
    TokenDTO findById(Long id);
//    List<Token> findByUserIdAndPage(Long userId, int page, int size);
}
