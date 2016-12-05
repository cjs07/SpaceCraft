package com.deepwelldevelopment.spacecraft.common.lib.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HexUtils {
    static final int[][] NEIGHBORS = new int[][]{{1, 0}, {1, -1}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}};

    public static int getDistance(Hex a1, Hex a2) {
        return (Math.abs(a1.q - a2.q) + Math.abs(a1.r - a2.r) + Math.abs(a1.q + a1.r - a2.q - a2.r)) / 2;
    }

    public static Hex getRoundedHex(double qq, double rr) {
        return HexUtils.getRoundedCubicHex(qq, rr, -qq - rr).toHex();
    }

    public static CubicHex getRoundedCubicHex(double x, double y, double z) {
        int rx = (int) Math.round(x);
        int ry = (int) Math.round(y);
        int rz = (int) Math.round(z);
        double xDiff = Math.abs((double) rx - x);
        double yDiff = Math.abs((double) ry - y);
        double zDiff = Math.abs((double) rz - z);
        if (xDiff > yDiff && xDiff > zDiff) {
            rx = -ry - rz;
        } else if (yDiff > zDiff) {
            ry = -rx - rz;
        } else {
            rz = -rx - ry;
        }
        return new CubicHex(rx, ry, rz);
    }

    public static ArrayList<Hex> getRing(int radius) {
        Hex h = new Hex(0, 0);
        for (int i = 0; i < radius; i++) {
            h = h.getNeighbor(4);
        }
        ArrayList<Hex> ring = new ArrayList<Hex>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < radius; j++) {
                ring.add(h);
                h = h.getNeighbor(i);
            }
        }
        return ring;
    }

    public static HashMap<String, Hex> generateHexes(int radius) {
        HashMap<String, Hex> results = new HashMap<>();
        Hex h = new Hex(0, 0);
        results.put(h.toString(), h);
        for (int k = 0; k < radius; k++) {
            h = h.getNeighbor(4);
            Hex hd = new Hex(h.q, h.r);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j <= k; j++) {
                    results.put(hd.toString(), hd);
                    hd = hd.getNeighbor(i);
                }
            }
        }
        return results;
    }

    public ArrayList<Hex> distributeRingRandomly(int radius, int entries, Random random) {
        ArrayList<Hex> ring = HexUtils.getRing(radius);
        ArrayList<Hex> results = new ArrayList<Hex>();
        float spacing = (float) ring.size() / (float) entries;
        int start = random.nextInt(ring.size());
        float pos = 0.0f;
        for (int i = 0; i < entries; i++) {
            results.add(ring.get(Math.round(pos)));
            pos += spacing;
        }
        return results;
    }

    public static class Pixel {
        public double x = 0.0;
        public double y = 0.0;

        public Pixel(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Hex toHex(int size) {
            double qq = 0.66666666666666666 * this.x / (double) size;
            double rr = (0.33333333333333333 * Math.sqrt(3.0) * (-this.y) - 0.33333333333333333 * this.x) / (double) size;
            return HexUtils.getRoundedHex(qq, rr);
        }
    }

    public static class CubicHex {
        public int x = 0;
        public int y = 0;
        public int z = 0;

        public CubicHex(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Hex toHex() {
            return new Hex(this.x, this.z);
        }
    }

    public static class Hex {
        public int q = 0;
        public int r = 0;

        public Hex(int q, int r) {
            this.q = q;
            this.r = r;
        }

        public CubicHex toCubicHex() {
            return new CubicHex(this.q, this.r, -this.q - this.r);
        }

        public Pixel toPixel(int size) {
            return new Pixel((double) size * 1.5 * (double) this.q, (double) size * Math.sqrt(3.0) * ((double) this.r + (double) this.q / 2.0));
        }

        public Hex getNeighbor(int direction) {
            int[] d = HexUtils.NEIGHBORS[direction];
            return new Hex(this.q + d[0], this.r + d[1]);
        }

        public boolean equals(Hex h) {
            return h.q == this.q && h.r == this.r;
        }

        @Override
        public String toString() {
            return "" + this.q + ":" + this.r;
        }
    }
}
