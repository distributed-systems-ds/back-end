package co.com.icesi.Eshop.api;

import co.com.icesi.Eshop.dto.request.CategoryDTO;
import co.com.icesi.Eshop.dto.response.CategoryResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(CategoryApi.BASE_URL)
public interface CategoryApi {
    String BASE_URL = "/api/categories";

    @PostMapping("/create")
    CategoryResponseDTO createCategory(@Valid @RequestBody CategoryDTO categoryResponseDTO);

    //TODO: Evaluate if it needs a put or a patch
    @PutMapping("/update")
    CategoryResponseDTO updateCategory(@Valid @RequestBody CategoryDTO categoryResponseDTO);

    @DeleteMapping("/delete")
    CategoryResponseDTO deleteCategory(@RequestBody String name);

    @GetMapping("/all")
    List<CategoryResponseDTO> getAllCategories();
}
