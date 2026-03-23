package ru.bsuedu.cad.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuedu.cad.lab.entity.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
