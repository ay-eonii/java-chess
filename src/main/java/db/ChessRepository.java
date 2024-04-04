package db;

import java.util.List;
import java.util.Optional;

public class ChessRepository {
    private final SquareDao squareDao = new SquareDao();
    private final TurnDao turnDao = new TurnDao();

    public Optional<ChessDto> findChess() {
        List<SquareDto> allSquares = squareDao.findAll();
        Optional<TurnDto> optionalTurnDto = turnDao.findTurn();
        if (isNotEmpty(allSquares) && optionalTurnDto.isPresent()) {
            return Optional.of(new ChessDto(allSquares, optionalTurnDto.get()));
        }
        return Optional.empty();
    }

    private boolean isNotEmpty(List<SquareDto> allSquares) {
        return !allSquares.isEmpty();
    }

    public void save(List<SquareDto> squareDtos, TurnDto turnDto) {
        reset();
        squareDao.addSquares(squareDtos);
        turnDao.addTurn(turnDto);
    }

    public void reset() {
        squareDao.deleteAll();
        turnDao.deleteAll();
    }
}
