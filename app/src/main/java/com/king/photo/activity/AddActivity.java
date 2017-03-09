package com.king.photo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entry.UploadTest;
import com.example.entry.Wall;
import com.example.family.R;
import com.example.tools.RunTime;
import com.example.tools.Tools;
import com.example.view.RoundProgressBar;
import com.king.photo.util.Bimp;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;


/**
 *
 */
public class AddActivity extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap ;
	private ProgressBar bar;
	private EditText add_et;
	private  String content;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		boolean b = getIntent().getBooleanExtra("isClear",false);
		if(b)Bimp.tempSelectBitmap.clear();
		bimap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_selectimg, null);
		setContentView(parentView);
		Init();
		TextView tbnButton = (TextView) findViewById(R.id.activity_selectimg_send);
		tbnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				StringBuffer sb = new StringBuffer();
//				for(ImageItem imageItem:Bimp.tempSelectBitmap){
//					sb.append(imageItem.imagePath+"\n");
//				}
//				Toast.makeText(AddActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
				 content = add_et.getText().toString().trim();
				if(content.length()<1){
					Tools.showToast(AddActivity.this,"说点什么吧");
					return;
				}
				if(Bimp.tempSelectBitmap.size()<1){
					Tools.showToast(AddActivity.this,"先添加点图片吧");
					return;
				}
				upload();
			}
		});
	}

	@Override
	protected void onResume() {
		if(content!=null&&content.length()>0){
			add_et.setText(content);
		}
		super.onResume();

	}

	@Override
	protected void onPause() {
		 content = add_et.getText().toString().trim();
		super.onPause();
	}

	public void  upload(){
	final String filePaths[] = new String[Bimp.tempSelectBitmap.size()];
	final int errorSize[] = new int[1];//上传失败的个数
	final List<String> urlList = new ArrayList<String>();
	for (int i=0;i<filePaths.length;i++){
		filePaths[i] = Bimp.tempSelectBitmap.get(i).getImagePath();
	}
	if(bar==null){
		bar = (ProgressBar) findViewById(R.id.add_progress);
		bar.setVisibility(View.VISIBLE);
		bar.setBackgroundColor(0x4087bf);
		//设置滚动条可见
		setProgressBarIndeterminateVisibility(true);
	}
	BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

		@Override
		public void onSuccess(List<BmobFile> files,List<String> urls) {
			Log.i("life","insertBatchDatasWithOne -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
//			if(urls.size()==1){//如果第一个文件上传完成
//				UploadTest test =new UploadTest(files.get(urls.size()-1),""+urls.size());
//				insertObject(test);
//				//movies.add(movie);
//			}else if(urls.size()==2){//第二个文件上传成功
//				Movie movie1 =new Movie("哈利波特2",files.get(1));
//				movies.add(movie1);
//				insertBatch(movies);
//			}
			BmobFile file = files.get(urls.size()-1);
			UploadTest test =new UploadTest(file,file.getFilename(),file.getFileUrl());
			insertObject(test);
			urlList.add(test.getUrl());
			if(filePaths.length==(urls.size()-errorSize[0])){
				bar.setVisibility(View.GONE);
				Toast.makeText(AddActivity.this,"发表图片成功！",Toast.LENGTH_SHORT).show();
				Wall wall = new Wall();
				wall.setUserId(RunTime.user.getObjectId());
				wall.setUserName(RunTime.user.getUsername());
				wall.setCommentNum(0);
				wall.setDianzanNum(0);
				wall.setUrl(urlList);
				 content = add_et.getText().toString().trim();
				wall.setContent(content);
				insertObject(wall);
				AddActivity.this.finish();
			}
		}

		@Override
		public void onError(int statuscode, String errormsg) {
		//	ShowToast("错误码"+statuscode +",错误描述："+errormsg);
			errorSize[0]++;
		}

		@Override
		public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
			//1、curIndex--表示当前第几个文件正在上传
			//2、curPercent--表示当前上传文件的进度值（百分比）
			//3、total--表示总的上传文件数
			//4、totalPercent--表示总的上传进度（百分比）
			bar.setProgress(totalPercent);
		}
	});
}
	private void insertObject(final BmobObject obj){
		obj.save( new SaveListener() {

			@Override
			public void done(Object o, BmobException e) {
//				if(e==null){
//					Tools.showToast(AddActivity.this,"-->创建数据成功：" + obj.getObjectId());
//				}else{
//					Tools.showToast(AddActivity.this,"-->创建数据失败：" );
//				}
			}
		});

	}
	public void Init() {
		
		pop = new PopupWindow(AddActivity.this);
		
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		add_et = (EditText) findViewById(R.id.add_text_et);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view
				.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AddActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);	
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(AddActivity.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(AddActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		//取消返回
		findViewById(R.id.activity_selectimg_canel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddActivity.this.finish();
			}
		});

	}

	@Override
	public boolean dispatchKeyShortcutEvent(KeyEvent event) {
		if(event.getAction()==KeyEvent.ACTION_DOWN){
			if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
				return true;
			}
		}
		return super.dispatchKeyShortcutEvent(event);
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.add_pic));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);
				
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for(int i=0;i<PublicWay.activityList.size();i++){
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			System.exit(0);
		}
		return true;
	}

}

