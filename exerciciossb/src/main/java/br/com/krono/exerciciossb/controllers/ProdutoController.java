package br.com.krono.exerciciossb.controllers;

import br.com.krono.exerciciossb.model.DTO.PageResponse;
import br.com.krono.exerciciossb.model.entity.Produto;
import br.com.krono.exerciciossb.model.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired //automaticamente coloca um objeto dentro do atributo
    private ProdutoRepository produtoRepository;

    @Operation(summary = "Salva ou atualiza um produto", description = "Recebe os dados de um produto. Se o ID for enviado e já existir," +
            " ele atualiza; caso contrário, cria um novo registro.")
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}) //mapeia a URL para o metodo
    public @ResponseBody Produto novoProduto( @RequestBody @Valid Produto produto){ //variavel que vem da URL
        produtoRepository.save(produto);
        return produto;
    }
    @Operation(summary = "Busca produto por ID", description = "Retorna os detalhes de um" +
            " único produto baseado no código identificador fornecido.")
    @GetMapping("/{id}")
    public Produto listarporId(@PathVariable int id){
        if (produtoRepository.existsById(id)){
            return produtoRepository.findById(id).orElse(null);
        }
        return null;
    }

    @Operation(summary = "Remove um produto", description = "Exclui permanentemente" +
            " um produto do banco de dados utilizando o ID informado.")
    @DeleteMapping( "/{id}")
    public Produto excluirProduto(@PathVariable int id){ //variavel que vem da URL
        if (produtoRepository.existsById(id)){
            Produto produto = produtoRepository.findById(id).orElse(null);
            produtoRepository.deleteById(id);
            return produto;
        }
        return null;

    }
    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista completa" +
            " com todos os produtos cadastrados, sem paginação.")
    @GetMapping( "/listar")
    public @ResponseBody Iterable<Produto> listarProdutos(){
        if (produtoRepository.count() > 0){
            return produtoRepository.findAll();
        }
        return null;
    }
    @Operation(summary = "Busca produtos por nome", description = "Procura produtos que contenham o texto informado no nome (busca parcial).")
    @GetMapping(path = "/nome/{nome}")
    public Iterable<Produto> obterProdutosPorNome(@PathVariable String nome){
        return produtoRepository.searchByNomeLike(nome);
    }

    @Operation(summary = "Lista produtos com paginação", description = "Organiza a lista de produtos em páginas. Você pode controlar o número da página" +
            " e a ordenação. O limite máximo é de 10 itens por vez.")
    @GetMapping("/pagina")
    public PageResponse<Produto> obterProdutosporPagina(
            @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC, sort = "id")Pageable pageable
            ){
        Page<Produto> page = produtoRepository.findAll(pageable);

        return new PageResponse<>(
         page.getContent(),
         page.getNumber(),
                page.getSize(),
         page.getTotalElements(),
            page.getTotalPages());
    }



}
