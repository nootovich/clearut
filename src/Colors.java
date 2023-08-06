public class Colors {

    private static final int[] BLUE1   = new int[]{
        0x012a4a, 0x013a63, 0x01497c, 0x014f86, 0x2a6f97, 0x2c7da0, 0x468faf, 0x61a5c2, 0x89c2d9, 0xa9d6e5
    };
    private static final int[] BLUE2   = new int[]{
        0x0d47a1, 0x1565c0, 0x1976d2, 0x1e88e5, 0x2196f3, 0x42a5f5, 0x64b5f6, 0x90caf9, 0xbbdefb, 0xe3f2fd
    };
    private static final int[] YELLOW1 = new int[]{
        0x76520e, 0x805b10, 0x926c15, 0xa47e1b, 0xb69121, 0xc9a227, 0xdbb42c, 0xedc531, 0xfad643, 0xffe169
    };
    private static final int[] YELLOW2 = new int[]{
        0xfcac5d, 0xfcb75d, 0xfcbc5d, 0xfcc75d, 0xfccc5d, 0xfcd45d, 0xfcdc5d, 0xfce45d, 0xfcec5d, 0xfcf45d
    };
    // TODO: temporary
    private static final int[] GREEN   = new int[]{
        0x0da147, 0x15c065, 0x19d276, 0x1ee588, 0x21f396, 0x42f5a5, 0x64f6b5, 0x90f9ca, 0xbbfbde, 0xe3fdf2
    };

    public static final int white = 0xFFFFFF;

    public static final int lgray = 0xFEEDED;

    public static int[] blue1() {
        return BLUE1;
    }

    public static int blue1(int index) {
        return BLUE1[index];
    }

    public static int[] blue2() {
        return BLUE2;
    }

    public static int blue2(int index) {
        return BLUE2[index];
    }

    public static int[] yellow1() {
        return YELLOW1;
    }

    public static int yellow1(int index) {
        return YELLOW1[index];
    }

    public static int[] yellow2() {
        return YELLOW2;
    }

    public static int yellow2(int index) {
        return YELLOW2[index];
    }

    public static int[] green() {
        return GREEN;
    }

    public static int green(int index) {
        return GREEN[index];
    }
}
