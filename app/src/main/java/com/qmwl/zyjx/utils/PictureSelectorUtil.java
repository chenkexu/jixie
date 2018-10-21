package com.qmwl.zyjx.utils;


import android.app.Activity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmwl.zyjx.R;


import java.util.List;

/**
 * TODO
 *
 * @author huangrui
 * @desc 选择图片工具类
 */
public class PictureSelectorUtil {





	/**
	 * 从相册选择图片
	 * 意见反馈页面用
	 * @param mContext     上下文信息
	 * @param maxSelectNum 最大选择数
	 * @param selectList   已选择列表
	 */
	public static void openGalleryUseByFeedback(Activity mContext, int maxSelectNum, List<LocalMedia> selectList) {
		// 进入相册 不需要的api可以不写
		PictureSelector.create(mContext)
				.openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
				.theme(R.style.picture_my_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
				.maxSelectNum(maxSelectNum)// 最大图片选择数量
				.minSelectNum(1)// 最小选择数量
				.imageSpanCount(3)// 每行显示个数
				.selectionMode(maxSelectNum > 1 ?
						PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
				.previewImage(true)// 是否可预览图片
				.previewVideo(true)// 是否可预览视频
				.enablePreviewAudio(true) // 是否可播放音频
				.isCamera(false)// 是否显示拍照按钮
				.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
				//.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
				//.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
				.enableCrop(false)// 是否裁剪
				.compress(true)// 是否压缩
				.synOrAsy(true)//同步true或异步false 压缩 默认同步
				//.compressSavePath(getPath())//压缩图片保存地址
				//.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
				.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
				//.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
				.hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
				.isGif(true)// 是否显示gif图片
				.freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .openClickSound(cb_voice.isChecked())// 是否开启点击声音
				.selectionMedia(selectList)// 是否传入已选图片
				//.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
				.previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
				//.cropCompressQuality(90)// 裁剪压缩质量 默认100
				.minimumCompressSize(100)// 小于100kb的图片不压缩
				//.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
				//.rotateEnabled(true) // 裁剪是否可旋转图片
				//.scaleEnabled(true)// 裁剪是否可放大缩小图片
				//.videoQuality()// 视频录制质量 0 or 1
				//.videoSecond()//显示多少秒以内的视频or音频也可适用
				//.recordVideoSecond()//录制视频秒数 默认60s
				.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
	}

	/**
	 * 拍照
	 * 意见反馈页面用
	 *
	 * @param mContext
	 * @param selectList
	 */
	public static void openCameraUseByFeedback(Activity mContext, List<LocalMedia> selectList){
		// 单独拍照
		PictureSelector.create(mContext)
				.openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
				.theme(R.style.picture_my_style)// 主题样式设置 具体参考 values/styles
				//.maxSelectNum(maxSelectNum)// 最大图片选择数量
				//.minSelectNum(1)// 最小选择数量
				//.selectionMode(cb_choose_mode.isChecked() ?
				//        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
				.previewImage(true)// 是否可预览图片
				//.previewVideo(cb_preview_video.isChecked())// 是否可预览视频
				//.enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
				//.isCamera(cb_isCamera.isChecked())// 是否显示拍照按钮
				//.enableCrop(cb_crop.isChecked())// 是否裁剪
				.compress(true)// 是否压缩
				.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
				//.withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
				.hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
				//.isGif(cb_isGif.isChecked())// 是否显示gif图片
				//.freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
				//.circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
				//.showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
				//.showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
				//.openClickSound(cb_voice.isChecked())// 是否开启点击声音
				.selectionMedia(selectList)// 是否传入已选图片
				.previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
				//.cropCompressQuality(90)// 裁剪压缩质量 默认为100
				.minimumCompressSize(100)// 小于100kb的图片不压缩
				//.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
				//.rotateEnabled() // 裁剪是否可旋转图片
				//.scaleEnabled()// 裁剪是否可放大缩小图片
				//.videoQuality()// 视频录制质量 0 or 1
				//.videoSecond()////显示多少秒以内的视频or音频也可适用
				.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
	}
}
