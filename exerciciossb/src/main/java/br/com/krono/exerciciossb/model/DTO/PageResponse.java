
package br.com.krono.exerciciossb.model.DTO;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
        ) {
}
