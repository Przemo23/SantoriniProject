package mygame;

public enum Floor {
    ZERO(0), GROUND(1), FIRST(2), SECOND(3), DOME(4);

    int height;

    Floor(int value){
        height = value;
    }
}

