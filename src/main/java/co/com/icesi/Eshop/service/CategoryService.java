package co.com.icesi.Eshop.service;

import co.com.icesi.Eshop.dto.request.CategoryDTO;
import co.com.icesi.Eshop.dto.response.CategoryResponseDTO;
import co.com.icesi.Eshop.mapper.CategoryMapper;
import co.com.icesi.Eshop.model.Category;
import co.com.icesi.Eshop.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;
    public CategoryResponseDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toCategory(categoryDTO);
        category.setCategoryId(UUID.randomUUID());
        return categoryMapper.toCategoryResponseDTO(categoryRepository.save(category));
    }

    public CategoryResponseDTO updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findByName(categoryDTO.getName()).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryMapper.toCategoryResponseDTO(categoryRepository.save(category));
    }




    public CategoryResponseDTO deleteCategory(String categoryName) {
        String categoryDelete = categoryName;
        if(categoryDelete.matches(".*\".*")){
            categoryDelete = categoryName.substring(1, categoryName.length() - 1);
        }
        Category category = categoryRepository.findByName(categoryDelete).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
        return categoryMapper.toCategoryResponseDTO(category);
    }
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponseDTO).collect(Collectors.toList());
    }
}
