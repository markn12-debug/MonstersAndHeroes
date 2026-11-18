// Used to create a cell object for each cell in the Board
// Can contain a marker or be null
public class BoardCell {
    private Marker marker;

    public BoardCell() {
        marker = null;
    }

    public boolean isEmpty() {
        return marker == null;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }

    public boolean hasMarker(char symbol) {
        return marker != null && marker.getSymbol() == symbol;
    }

    public String toString() {
        if (isEmpty()) {
            return " ";
        }
        return String.valueOf(marker.getSymbol());
    }
}
