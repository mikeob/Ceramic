/**
 * Written by Mike O'Beirne
 * michael.obeirne@gmail.com
 * 
 * Didn't get this one completely. Failed some test cases. Realized a little too late that
 * I modeled putting the pieces in slightly wrong. Completed 5/6 of judge data - failed on the last.
 */
import java.awt.Point;
import java.util.Scanner;

public class Main {

	static byte[][] floor;

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		int N = in.nextInt();
		int setNumber = 0;

		// Main algorithm
		while (setNumber++ < N) {

			System.out.println("Data Set " + setNumber);

			floor = new byte[6][6];
			byte[] available = new byte[9];

			for (int i = 0; i < 9; i++) {
				available[i] = (byte) in.nextInt();
			}

			if (backTrack(available, new Point(0, 0), 0)) {
				// Success!
			} else {
				System.out.println("The floor may not be tiled.");
				System.out.println();
			}
		}

		System.out.println("End of Output");
		in.close();
	}

	public static boolean backTrack(byte[] available, Point next, int tilesUsed) {

		// We're done!
		if (tilesUsed == 9) {
			System.out.println("The floor may be tiled.");
			printBoard();
			return true;
		}

		if (next == null) {
			System.out.println("Failed attempt");
			printBoard();
			return false;
		}

		int row = next.x;
		int col = next.y;

		for (int i = 0; i < 9; i++) {
			if (available[i] > 0) {

				byte[][] copy = deepClone(floor);
				boolean placed = false;

				if (placeShape(available[i], (byte) 0, row, col, (byte) (i + 1))) {
					placed = true;
				} else if (placeShape(available[i], (byte) 1, row, col,
						(byte) (i + 1))) {
					placed = true;
				} else if (placeShape(available[i], (byte) 2, row, col,
						(byte) (i + 1))) {
					placed = true;
				} else if (placeShape(available[i], (byte) 3, row, col,
						(byte) (i + 1))) {
					placed = true;
				}

				if (placed) {
					byte previous = available[i];
					available[i] = 0;
					// If path unsuccessful, reset board to what it was
					if (!backTrack(available, findNextOpen(), tilesUsed + 1)) {
						floor = copy;
						available[i] = previous;
					} else {
						return true;
					}
				}

			}
		}
		return false;

	}

	public static void printBoard() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				char out = '-';
				switch (floor[i][j]) {
				case 1:
					out = 'A';
					break;
				case 2:
					out = 'B';
					break;
				case 3:
					out = 'C';
					break;
				case 4:
					out = 'D';
					break;
				case 5:
					out = 'E';
					break;
				case 6:
					out = 'F';
					break;
				case 7:
					out = 'G';
					break;
				case 8:
					out = 'H';
					break;
				case 9:
					out = 'I';
				}
				System.out.print(out);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static Point findNextOpen() {

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (floor[i][j] == 0) {
					return new Point(i, j);
				}
			}
		}

		return null;

	}

	public static boolean canPlace(int[] toPlace, byte number) {

		for (int i = 0; i < 4; i++) {

			int x = toPlace[2 * i];
			int y = toPlace[2 * i + 1];

			if (x < 0 || x > 5 || y < 0 || y > 5 || floor[x][y] != 0)
				return false;
		}

		// If we reach here, we can place the piece!
		for (int i = 0; i < 4; i++) {
			int x = toPlace[2 * i];
			int y = toPlace[2 * i + 1];

			floor[x][y] = number;
		}

		return true;

	}

	// Attempts to place a shape with an orientation at a particular row/col
	public static boolean placeShape(int shape, byte rotation, int row,
			int col, byte number) {

		int[] placement;

		switch (shape) {

		// XXXX
		case 1:
			if (rotation == 0) {

				placement = new int[] { row, col, row, col + 1, row, col + 2,
						row, col + 3 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 1) {
				placement = new int[] { row, col, row + 1, col, row + 2, col,
						row + 3, col };
				if (canPlace(placement, number)) {
					return true;
				}

			}
			break;

		// XX
		// XX
		case 2:

			placement = new int[] { row, col, row + 1, col, row, col + 1,
					row + 1, col + 1 };
			if (canPlace(placement, number)) {
				return true;
			}
			break;

		// XX
		// XX
		case 3:
			if (rotation == 0) {
				placement = new int[] { row, col, row, col + 1, row + 1,
						col + 1, row + 1, col + 2 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 1) {
				placement = new int[] { row, col, row + 1, col, row + 1,
						col - 1, row + 2, col - 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 2) {
				placement = new int[] { row, col, row, col + 1, row + 1,
						col + 1, row + 1, col + 2 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 3) {
				placement = new int[] { row, col, row - 1, col, row - 1,
						col + 1, row - 2, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			}
			break;
		// XX
		// XX
		case 4:
			if (rotation == 0) {
				placement = new int[] { row, col, row, col + 1, row + 1, col,
						row + 1, col - 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 1) {
				placement = new int[] { row, col, row - 1, col, row, col + 1,
						row + 1, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 2) {
				placement = new int[] { row, col, row, col + 1, row - 1,
						col + 1, row - 1, col + 2 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 3) {
				placement = new int[] { row, col, row - 1, col, row, col + 1,
						row + 1, col + 1 };
				if (canPlace(placement, number))
					return true;
			}

			break;
		// X
		// XXX
		case 5:
			if (rotation == 0) {
				placement = new int[] { row, col, row + 1, col, row + 1,
						col + 1, row + 1, col + 2 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 1) {
				placement = new int[] { row, col, row - 1, col, row - 2, col,
						row - 2, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 2) {
				placement = new int[] { row, col, row, col + 1, row, col + 2,
						row + 1, col + 2 };
				if (canPlace(placement, number)) {
					return true;
				}

			} else if (rotation == 3) {
				placement = new int[] { row, col, row + 1, col, row + 2, col,
						row + 2, col - 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			}
			break;
		// XXX
		// X
		case 6:
			if (rotation == 0) {
				placement = new int[] { row, col, row + 1, col, row, col + 1,
						row, col + 2 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 1) {
				placement = new int[] { row, col, row, col + 1, row + 1,
						col + 1, row + 2, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 2) {
				placement = new int[] { row, col, row, col + 1, row, col + 2,
						row - 1, col + 2 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 3) {
				placement = new int[] { row, col, row - 1, col, row - 2, col,
						row, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			}
			break;
		// X
		// XXX
		case 7:
			if (rotation == 0) {
				placement = new int[] { row, col, row, col + 1, row, col + 2,
						row - 1, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 1) {
				placement = new int[] { row, col, row + 1, col, row + 2, col,
						row + 1, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 2) {
				placement = new int[] { row, col, row, col + 1, row, col + 2,
						row + 1, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			} else if (rotation == 3) {
				placement = new int[] { row, col, row, col + 1, row - 1,
						col + 1, row + 1, col + 1 };
				if (canPlace(placement, number)) {
					return true;
				}
			}
			break;
		}

		return false;
	}

	public static byte[][] deepClone(byte[][] original) {

		byte[][] ans = original.clone();
		for (int i = 0; i < 6; i++) {
			ans[i] = original[i].clone();
		}

		return ans;
	}

}
