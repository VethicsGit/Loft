package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vethics.loft.DashBoardActivity;
import com.vethics.loft.R;
import com.vethics.loft.ViewDownloadedVideoActivity;

import java.io.File;

import interfaces.OnRecyclerViewItemClickListner;
import io.realm.Realm;
import io.realm.RealmResults;
import model.KeyModel;
import utils.AndroidUtils;

/**
 * Created by Chirag on 2/9/2018.
 */

public class DownloadedVideoAdapter extends RecyclerView.Adapter<DownloadedVideoAdapter.ViewHolder> {
    private Context context;
    private RealmResults<KeyModel> keyModelList;
    private final OnRecyclerViewItemClickListner listener;
    private String value;
    private int mExpandedPosition = -1;
    private DashBoardActivity dashBoardActivity;
    private Realm realm;
    private final String TAG = getClass().getCanonicalName();

    public DownloadedVideoAdapter(Context context, RealmResults<KeyModel> keyModelList, OnRecyclerViewItemClickListner listener, String value) {
        this.context = context;
        this.keyModelList = keyModelList;
        this.listener = listener;
        this.value = value;
        realm = Realm.getDefaultInstance();
        dashBoardActivity = (DashBoardActivity) context;
    }

    @Override
    public DownloadedVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_downloaded_video, parent, false);
        return new DownloadedVideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DownloadedVideoAdapter.ViewHolder holder, final int position) {
        if (value.equalsIgnoreCase("course"))
            holder.tvDownloadedVideoFileName.setText(AndroidUtils.wordFirstCap(keyModelList.get(position).getCoursename()));
        else if (value.equalsIgnoreCase("video")) {
            holder.tvDownloadedVideoFileName.setText(AndroidUtils.wordFirstCap(keyModelList.get(position).getFilename()));
            holder.tvDownloadedVideoFileSize.setText(AndroidUtils.size(keyModelList.get(position).getFileSize()));
        }
        if (holder.getAdapterPosition() == keyModelList.size() - 1) {
            holder.llViewDownload.setVisibility(View.INVISIBLE);
        }

        /*holder.llDownloadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });*/

        final boolean isExpanded = holder.getAdapterPosition() == mExpandedPosition;
        holder.flDownloadVideo.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.llDownloadVideo.setActivated(isExpanded);
        holder.llDownloadVideo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                //recyclerView.smoothScrollToPosition(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        holder.flDownloadVideo.removeAllViews();

        final int id = keyModelList.get(position).getId();
        RealmResults<KeyModel> keyModelList1 = realm.where(KeyModel.class)
                .beginGroup()
                .equalTo("downloaded", "true")
                .and()
                .equalTo("coursename", keyModelList.get(position).getCoursename())
                .endGroup()
                .findAll();
        Log.e(TAG, "id result : " + keyModelList1.toString());

        for (int j = 0; j < keyModelList1.size(); j++) {
            View v = LayoutInflater.from(context).inflate(R.layout.custom_downloaded_video_content, null, false);
            final TextView tv_downloaded_video_file_name = (TextView) v.findViewById(R.id.tv_downloaded_video_file_name);
            final TextView tv_downloaded_video_file_size = (TextView) v.findViewById(R.id.tv_downloaded_video_file_size);
            ImageView iv_downloaded_video_delete = (ImageView) v.findViewById(R.id.iv_downloaded_video_delete);
            tv_downloaded_video_file_name.setText(keyModelList1.get(j).getFilename());
            tv_downloaded_video_file_size.setText(AndroidUtils.size(keyModelList1.get(j).getFileSize()));
            holder.flDownloadVideo.addView(v);

            final int finalJ = j;
            holder.flDownloadVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, tv_downloaded_video_file_name.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ViewDownloadedVideoActivity.class);
                    intent.putExtra("videoFileName", tv_downloaded_video_file_name.getText());
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });

            iv_downloaded_video_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    realm.executeTransaction(new Realm.Transaction() {

                        public void execute(Realm realm) {
                            RealmResults<KeyModel> keyModelList1 = realm.where(KeyModel.class)
                                    .beginGroup()
                                    .equalTo("filename", tv_downloaded_video_file_name.getText().toString())
                                    .and()
                                    .equalTo("coursename", keyModelList.get(position).getCoursename())
                                    .endGroup()
                                    .findAll();
                            File file = new File(context.getExternalFilesDir("downloads"), keyModelList1.get(0).getFilename());
                            file.delete();
                            keyModelList1.deleteFromRealm(0);
                            notifyDataSetChanged();
                            /*keyModelList1.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<KeyModel>>() {
                                @Override
                                public void onChange(RealmResults<KeyModel> keyModels, @Nullable OrderedCollectionChangeSet changeSet) {
                                }
                            });*/
                        }
                    });

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return keyModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDownloadedVideoFileName, tvDownloadedVideoFileSize;
        LinearLayout llViewDownload, llDownloadVideo;
        LinearLayout flDownloadVideo;

        ViewHolder(View itemView) {
            super(itemView);
            tvDownloadedVideoFileName = (TextView) itemView.findViewById(R.id.tv_downloaded_video_file_name);
            tvDownloadedVideoFileSize = (TextView) itemView.findViewById(R.id.tv_downloaded_video_file_size);
            llViewDownload = (LinearLayout) itemView.findViewById(R.id.ll_view_download);
            llDownloadVideo = (LinearLayout) itemView.findViewById(R.id.ll_download_video);
            flDownloadVideo = (LinearLayout) itemView.findViewById(R.id.downloadContainer1);
        }
    }
}
