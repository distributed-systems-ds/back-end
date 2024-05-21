package co.com.icesi.Eshop.Unit.service;

import co.com.icesi.Eshop.Unit.util.CrudTest;
import co.com.icesi.Eshop.Unit.util.Matcher.CategoryMatcher;
import co.com.icesi.Eshop.dto.request.CategoryDTO;
import co.com.icesi.Eshop.dto.response.CategoryResponseDTO;
import co.com.icesi.Eshop.mapper.CategoryMapper;
import co.com.icesi.Eshop.mapper.CategoryMapperImpl;
import co.com.icesi.Eshop.model.Category;
import co.com.icesi.Eshop.repository.CategoryRepository;
import co.com.icesi.Eshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static co.com.icesi.Eshop.Unit.util.data.DefaultDataGenerator.defaultCategory;
import static co.com.icesi.Eshop.Unit.util.data.DefaultDataGenerator.defaultCategoryDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest  implements CrudTest {
    private CategoryMapper categoryMapper;
    private CategoryService categoryService;
    private  CategoryRepository categoryRepository;


    @BeforeEach
    public  void init(){
        categoryRepository = mock(CategoryRepository.class);
        categoryMapper = spy(CategoryMapperImpl.class);
        categoryService = new CategoryService(categoryMapper,categoryRepository);
    }

    @Test
    @Override
    public void createTest() {
        CategoryResponseDTO expected = new CategoryResponseDTO();
        expected.setName(defaultCategory().getName());
        expected.setDescription(defaultCategory().getDescription());

        when(categoryMapper.toCategory(defaultCategoryDTO())).thenReturn(defaultCategory());
        when(categoryMapper.toCategoryResponseDTO(any())).thenReturn(expected);

        CategoryResponseDTO result = categoryService.createCategory(defaultCategoryDTO());
        verify(categoryMapper,times(1)).toCategory(any());
        verify(categoryMapper,times(1)).toCategoryResponseDTO(any());
        verify(categoryRepository,times(1)).save(argThat(new CategoryMatcher(defaultCategory())));
        assertNotNull(result);
        assertEquals(expected,result);
    }


    @Test
    @Override
    public void readTest() {
        Category category1 = new Category();
        category1.setCategoryId(UUID.randomUUID());
        Category category2 = new Category();
        category2.setCategoryId(UUID.randomUUID());
        List<Category> categories = Arrays.asList(category1, category2);

        CategoryResponseDTO categoryResponseDTO1 = new CategoryResponseDTO();

        CategoryResponseDTO categoryResponseDTO2 = new CategoryResponseDTO();

        List<CategoryResponseDTO> expectedResponse = Arrays.asList(categoryResponseDTO1, categoryResponseDTO2);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toCategoryResponseDTO(category1)).thenReturn(categoryResponseDTO1);
        when(categoryMapper.toCategoryResponseDTO(category2)).thenReturn(categoryResponseDTO2);


        List<CategoryResponseDTO> result = categoryService.getAllCategories();


        verify(categoryRepository).findAll();
        verify(categoryMapper).toCategoryResponseDTO(category1);
        verify(categoryMapper).toCategoryResponseDTO(category2);
        assertEquals(expectedResponse, result);
    }

    @Test
    @Override
    public void updateTest() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("New Name");
        categoryDTO.setDescription("New Description");

        Category category = new Category();
        category.setCategoryId(UUID.randomUUID());
        category.setName("Old Name");
        category.setDescription("Old Description");

        Category updatedCategory = new Category();
        updatedCategory.setCategoryId(UUID.fromString("a35182a4-ff13-11ed-be56-0242ac120002"));
        updatedCategory.setName("New Name");
        updatedCategory.setDescription("New Description");

        CategoryResponseDTO expectedResponse = new CategoryResponseDTO();
        expectedResponse.setName("New Name");
        expectedResponse.setDescription("New Description");

        when(categoryRepository.findByName(categoryDTO.getName())).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(updatedCategory);
        when(categoryMapper.toCategoryResponseDTO(updatedCategory)).thenReturn(expectedResponse);

        // Act
        CategoryResponseDTO result = categoryService.updateCategory(categoryDTO);

        // Assert
        verify(categoryRepository).findByName(categoryDTO.getName());
        verify(categoryRepository).save(category);
        verify(categoryMapper).toCategoryResponseDTO(updatedCategory);
        assertEquals(expectedResponse, result);
    }

    @Test
    public void testUpdateCategory_CategoryNotFound() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Non-existent Category");
        categoryDTO.setDescription("New Description");

        when(categoryRepository.findByName(categoryDTO.getName())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> categoryService.updateCategory(categoryDTO));
        verify(categoryRepository).findByName(categoryDTO.getName());
        verify(categoryRepository, never()).save(any());
        verify(categoryMapper, never()).toCategoryResponseDTO(any(Category.class));
    }

    @Test
    @Override
    public void deleteTest() {
        String categoryName = "Category 1";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(defaultCategory()));

        CategoryResponseDTO expectedResponse = new CategoryResponseDTO();
        expectedResponse.setName(categoryName);
        when(categoryMapper.toCategoryResponseDTO(defaultCategory())).thenReturn(expectedResponse);

        CategoryResponseDTO result = categoryService.deleteCategory(categoryName);

        verify(categoryRepository, times(1)).findByName(categoryName);
        verify(categoryRepository, times(1)).delete(defaultCategory());
        verify(categoryMapper, times(1)).toCategoryResponseDTO(defaultCategory());
        assertEquals(expectedResponse, result);

    }

    @Test
    public void testDeleteCategory_CategoryNotFound() {
        // Arrange
        String categoryName = "Non-existent Category";
        String categoryDelete = categoryName.substring(1, categoryName.length() - 1);

        when(categoryRepository.findByName(categoryDelete)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> categoryService.deleteCategory(categoryName));
        verify(categoryRepository, never()).delete(any());
        verify(categoryMapper, never()).toCategoryResponseDTO(any(Category.class));
    }
}
