package co.com.icesi.Eshop.controller;

import co.com.icesi.Eshop.api.CategoryApi;
import co.com.icesi.Eshop.dto.request.CategoryDTO;
import co.com.icesi.Eshop.dto.response.CategoryResponseDTO;
import co.com.icesi.Eshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController implements CategoryApi {
    private final CategoryService categoryService;
    /**
     * @param categoryDTO
     * @return
     */
    @Override
    public CategoryResponseDTO createCategory(CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    /**
     * @param categoryDTO
     * @return
     */
    @Override
    public CategoryResponseDTO updateCategory(CategoryDTO categoryDTO) {
        return categoryService.updateCategory(categoryDTO);
    }

    /**
     * @param categoryMame
     * @return
     */
    @Override
    public CategoryResponseDTO deleteCategory(String categoryMame) {
        return categoryService.deleteCategory(categoryMame);
    }

    /**
     * @return
     */
    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
