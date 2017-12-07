package repository;

import model.City;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
	List<City> findByBoards_Id(Long id);
	List<City> findAll();
}