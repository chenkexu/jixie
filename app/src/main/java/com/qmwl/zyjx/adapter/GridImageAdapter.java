package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.luck.picture.lib.entity.LocalMedia;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.utils.GlideUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择
 *
 * @author stephen.shen
 * @date 2018-08-06 13:49
 */
public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder> {
	
	private List<LocalMedia> list = new ArrayList<>();
	private Context context;
	private LayoutInflater mInflater;
	/**
	 * 点击添加图片跳转
	 */
	private OnAddPicClickListener mOnAddPicClickListener;

	/**
	 * 最大选择数
	 */
	private int selectMax;

	public interface OnAddPicClickListener {
		void onAddPicClick();
	}

	public GridImageAdapter(Context context, int selectMax, OnAddPicClickListener mOnAddPicClickListener) {
		this.context = context;
		this.selectMax = selectMax;
		mInflater = LayoutInflater.from(context);
		this.mOnAddPicClickListener = mOnAddPicClickListener;
	}
	
	public void setList(List<LocalMedia> list) {
		this.list = list;
	}

	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.user_feedback_item_filter_image,
				parent, false);
		final ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//		LogUtil.d("Position: "+position+", isShowAddItem: "+isShowAddItem(position));
		// 显示增加图片按钮
		if (isShowAddItem(position)) {
			holder.mImg.setImageResource(R.mipmap.user_feedback_add_image);
			holder.mImg.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View view) {
				   // 增加图片
				   mOnAddPicClickListener.onAddPicClick();
			   }
		   });
           holder.llDel.setVisibility(View.GONE);
		} else {
			GlideUtils.loadImage(context, holder.mImg, list.get(position).getCompressPath(), R.color.c666666, R.color.c666666);
			holder.llDel.setVisibility(View.VISIBLE);
			holder.llDel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

			   int index = holder.getAdapterPosition();
				// 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
				// 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
				if (index != RecyclerView.NO_POSITION) {
					list.remove(index);
					notifyItemRemoved(index);
					notifyItemRangeChanged(index, list.size());
				}
			}});
		}
	}

	@Override
	public int getItemCount() {
//		LogUtil.d("selectMax: " + selectMax);
		if (list.size() < selectMax) {
//			LogUtil.d("getItemCount: " + (list.size() + 1));
			return list.size() + 1;
		} else {
//			LogUtil.d("getItemCount: " + list.size());
			return list.size();
		}
	}

	private boolean isShowAddItem(int position) {
		int size = list.size() == 0 ? 0 : list.size();
		return position == size;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView mImg;
		LinearLayout llDel;

		public ViewHolder(View view) {
			super(view);
			mImg = (ImageView) view.findViewById(R.id.iv_user_feedback_item_image);
			llDel = (LinearLayout) view.findViewById(R.id.ll_user_feedback_item_del);
		}
	}
}
