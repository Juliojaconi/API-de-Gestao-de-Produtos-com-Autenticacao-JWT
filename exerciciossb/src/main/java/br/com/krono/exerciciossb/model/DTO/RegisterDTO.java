package br.com.krono.exerciciossb.model.DTO;

import br.com.krono.exerciciossb.model.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
