package xi.falcon.footballmanager;

public class FootballPlayer {
    public int id;
    public String ten;
    public int namsinh;
    public String team;
    public byte[] anh;

    public FootballPlayer(int id, String ten, int namsinh, String team, byte[] anh) {
        this.id = id;
        this.ten = ten;
        this.namsinh = namsinh;
        this.team = team;
        this.anh = anh;
    }
}
