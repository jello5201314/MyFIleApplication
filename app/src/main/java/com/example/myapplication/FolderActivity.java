package com.example.myapplication;
 
import java.io.IOException; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 
 
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.AdapterView; 
import android.widget.AdapterView.OnItemClickListener; 
import android.widget.ListView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class FolderActivity extends Activity implements OnItemClickListener,OnClickListener { 
 
  private ListView folderLv; 
  private TextView foldernowTv; 
  private SimpleAdapter sAdapter; 
  private List<Map<String, Object>> aList; 
  private String baseFile;
  private static final int REQUEST_EXTERNAL_STORAGE = 1;
  private TextView titleTv;
  private static String[] PERMISSIONS_STORAGE = {
          "android.permission.READ_EXTERNAL_STORAGE",
          "android.permission.WRITE_EXTERNAL_STORAGE" };
  @Override 
  protected void onCreate(Bundle savedInstanceState) { 
    // TODO Auto-generated method stub 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.mypage_folder); 
    baseFile=GetFilesUtils.getInstance().getBasePath(); 


//    titleTv=(TextView) findViewById(R.id.mtitle);
//    titleTv.setText("本地文件");
    verifyStoragePermissions(this);
    folderLv=(ListView) findViewById(R.id.folder_list); 
    foldernowTv=(TextView) findViewById(R.id.folder_now); 
    foldernowTv.setText(baseFile); 
    foldernowTv.setOnClickListener(this); 
    aList=new ArrayList<Map<String,Object>>(); 
    sAdapter=new SimpleAdapter(this, aList,R.layout.item_yun_file, new String[]{"fImg","fName","fInfo"},
        new int[]{R.id.folder_img,R.id.folder_name,R.id.folder_info}); 
    folderLv.setAdapter(sAdapter); 
    folderLv.setOnItemClickListener(this); 
    try { 
      loadFolderList(baseFile); 
    } catch (IOException e) { 
      // TODO Auto-generated catch block 
      e.printStackTrace(); 
    } 
  }

  //然后通过一个函数来申请
  public static void verifyStoragePermissions(Activity activity) {
    try {
      //检测是否有写的权限
      int permission = ActivityCompat.checkSelfPermission(activity,
              "android.permission.WRITE_EXTERNAL_STORAGE");
      if (permission != PackageManager.PERMISSION_GRANTED) {
        // 没有写的权限，去申请写的权限，会弹出对话框
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadFolderList(String file) throws IOException{ 
    List<Map<String, Object>> list=GetFilesUtils.getInstance().getSonNode(file); 
    if(list!=null){ 
      Collections.sort(list, GetFilesUtils.getInstance().defaultOrder()); 
      aList.clear(); 
      for(Map<String, Object> map:list){ 
        String fileType=(String) map.get(GetFilesUtils.FILE_INFO_TYPE); 
        Map<String,Object> gMap=new HashMap<String, Object>(); 
        if(map.get(GetFilesUtils.FILE_INFO_ISFOLDER).equals(true)){ 
          gMap.put("fIsDir", true); 
          gMap.put("fImg",R.drawable.icon_local_file );
          gMap.put("fInfo", map.get(GetFilesUtils.FILE_INFO_NUM_SONDIRS)+"个文件夹和"+ 
              map.get(GetFilesUtils.FILE_INFO_NUM_SONFILES)+"个文件"); 
        }else{ 
          gMap.put("fIsDir", false); 
          if(fileType.equals("txt")||fileType.equals("text")){ 
            gMap.put("fImg", R.drawable.icon_filter);
          }else{ 
            gMap.put("fImg", R.drawable.icon_space);
          } 
          gMap.put("fInfo","文件大小:"+GetFilesUtils.getInstance().getFileSize(map.get(GetFilesUtils.FILE_INFO_PATH).toString())); 
        } 
        gMap.put("fName", map.get(GetFilesUtils.FILE_INFO_NAME)); 
        gMap.put("fPath", map.get(GetFilesUtils.FILE_INFO_PATH)); 
        aList.add(gMap); 
      } 
    }else{ 
      aList.clear(); 
    } 
    sAdapter.notifyDataSetChanged(); 
    foldernowTv.setText(file); 
  } 
 
  @Override 
  public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
    // TODO Auto-generated method stub 
    try { 
      if(aList.get(position).get("fIsDir").equals(true)){ 
        loadFolderList(aList.get(position).get("fPath").toString()); 
      }else{ 
        Toast.makeText(this, "这是文件，处理程序待添加", Toast.LENGTH_SHORT).show(); 
      } 
    } catch (IOException e) { 
      // TODO Auto-generated catch block 
      e.printStackTrace(); 
    } 
  } 
 
  @Override 
  public void onClick(View v) { 
    // TODO Auto-generated method stub 
    if(v.getId()==R.id.folder_now){ 
      try { 
        String folder=GetFilesUtils.getInstance().getParentPath(foldernowTv.getText().toString()); 
        if(folder==null){ 
          Toast.makeText(this, "无父目录，待处理", Toast.LENGTH_SHORT).show(); 
        }else{ 
          loadFolderList(folder); 
        } 
      } catch (IOException e) { 
        // TODO Auto-generated catch block 
        e.printStackTrace(); 
      } 
    } 
  } 
   
} 