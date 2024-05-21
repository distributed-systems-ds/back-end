package co.com.icesi.Eshop.mapper;

import co.com.icesi.Eshop.dto.request.CategoryDTO;
import co.com.icesi.Eshop.dto.response.CategoryResponseDTO;
import co.com.icesi.Eshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryDTO category);

   // Category toCategory(CategoryResponseDTO category);

  //  CategoryDTO toCategoryDTO(Category category);

   // CategoryDTO toCategoryDTO(CategoryResponseDTO category);

    CategoryResponseDTO toCategoryResponseDTO(Category category);

   // CategoryResponseDTO toCategoryResponseDTO(CategoryDTO categoryDTO);

}
