package repository;

import model.Board;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {

	List<Board> findByIduser(Long iduser);
}