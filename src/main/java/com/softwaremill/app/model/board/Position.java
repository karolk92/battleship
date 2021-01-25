package com.softwaremill.app.model.board;

import com.softwaremill.app.exception.shot.PositionMalformedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Position {
    private char verticalPosition;
    private int horizontalPosition;

    public Position(String position) {
        validatePosition(position);
        this.verticalPosition = position.charAt(0);
        this.horizontalPosition = Integer.parseInt(position.substring(1));
    }

    private Position(char verticalPosition, int horizontalPosition) {
        validateVerticalRange(verticalPosition);
        validateHorizontalRangeLength(horizontalPosition);
        this.verticalPosition = verticalPosition;
        this.horizontalPosition = horizontalPosition;
    }

    public Position incrementVerticalPosition() {
        return new Position((char) (this.verticalPosition + 1), this.horizontalPosition);
    }

    public Position incrementHorizontalPosition() {
        return new Position(this.verticalPosition, this.horizontalPosition + 1);
    }

    private void validatePosition(String position) {
        validateLength(position);
        validateVerticalRange(position.charAt(0));
        validateHorizontalRange(position.substring(1));
    }

    private void validateHorizontalRange(String horizontalPosition) {
        try {
            int position = Integer.parseInt(horizontalPosition);
            validateHorizontalRangeLength(position);
        } catch(NumberFormatException ex) {
            throw new PositionMalformedException("Horizontal position malformed");
        }
    }

    private void validateHorizontalRangeLength(int position) {
        if(position < 0 || position > 10) {
            throw new PositionMalformedException("Horizontal position out of range");
        }
    }

    private void validateVerticalRange(char verticalPosition) {
        if ((int) verticalPosition < 65 || (int) verticalPosition > 74) {
            throw new PositionMalformedException("Vertical position out of range");
        }
    }

    private void validateLength(String position) {
        if (position.length() < 2 || position.length() > 3) {
            throw new PositionMalformedException("Invalid position: " + position);
        }
    }
}
