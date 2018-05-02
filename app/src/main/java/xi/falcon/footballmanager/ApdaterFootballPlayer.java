package xi.falcon.footballmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ApdaterFootballPlayer extends BaseAdapter {
    Activity context;
    ArrayList<FootballPlayer> list;

    public ApdaterFootballPlayer(Activity context, ArrayList<FootballPlayer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row, null);
        ImageView imgAvatar = (ImageView) row.findViewById(R.id.imgAvatar);
        TextView txtID = (TextView) row.findViewById(R.id.txtID);
        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtNamSinh = (TextView) row.findViewById(R.id.txtNamSinh);
        TextView txtTeam = (TextView) row.findViewById(R.id.txtTeam);
        Button btnSua = (Button) row.findViewById(R.id.btnSua);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);

        final FootballPlayer footballPlayer = list.get(position);
        txtID.setText(footballPlayer.id + "");
        txtTen.setText(footballPlayer.ten + "");
        txtNamSinh.setText(footballPlayer.namsinh + "");
        txtTeam.setText(footballPlayer.team + "");

        Bitmap bmAvatar = BitmapFactory.decodeByteArray(footballPlayer.anh, 0, footballPlayer.anh.length);
        imgAvatar.setImageBitmap(bmAvatar);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("ID", footballPlayer.id);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(footballPlayer.id);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return row;
    }

    private void delete(int idPlayer)
    {
        SQLiteDatabase database = Database.initDatabase(context, "FootballPlayer.sqlite");
        database.delete("Player", "ID = ?", new String[]{idPlayer + ""});
        list.clear();

        Cursor cursor = database.rawQuery("SELECT * FROM Player", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            int namsinh = cursor.getInt(2);
            String team = cursor.getString(3);
            byte[] anh = cursor.getBlob(4);
            list.add(new FootballPlayer(id, ten, namsinh, team, anh));
        }
        notifyDataSetChanged();
    }
}
