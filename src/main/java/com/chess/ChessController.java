package com.chess;

import com.ai.Action;
import com.ai.MiniMax;
import com.ai.ZobristHashing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessController {

    @GetMapping("/chessPosition/bestMove")
    public ResponseEntity<MoveResponse> search(
            @RequestParam String fen,
            @RequestParam(required = false) Long clockTime
    ) {
        Position position = PositionGenerator.fromFEN(fen);
        Move realMove = (Move) new MiniMax(new ChessBreakTest(4), new ZobristHashing()).search(position);
        return ResponseEntity.ok(
                new MoveResponse(
                        realMove.getStartingSquare(),
                        realMove.getEndingSquare(),
                        false,
                        false
                )
        );
    }
}
