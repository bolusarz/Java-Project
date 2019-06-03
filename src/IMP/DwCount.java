package IMP;

class DwCount {
    int dead;
    int wounded;

    DwCount(int d, int w) {
        dead = d;
        wounded = w;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return  false;
        if (getClass() != obj.getClass()) return false;
        DwCount other = (DwCount) obj;
        if (dead != other.dead) {
            return false;
        }
        return wounded == other.wounded;
    }
}
