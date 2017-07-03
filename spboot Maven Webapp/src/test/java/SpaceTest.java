
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SpaceTest {

	public static void main(String[] args) {
//		 String str_ = "    71.25    59.75        0.000000    ";
//		 String str = str_.replace("\r\n", "");
//		 String[] str_arr = str.trim().split("\\s+");
//		 if(str_arr != null && str_arr.length > 0){
//			 for(int i=0;i<str_arr.length;i++){
//				 String index_str = str_arr[i];
//				 str_ = str_.replace(index_str, i+"num"); 
//			 }
//			 System.out.println(str_);
//		 }
		String nn = "E://test.txt";
		OutputStreamWriter bw = null;
		try{
			bw = new OutputStreamWriter(new FileOutputStream(nn, true),"UTF-8");
			int len = 10;
			for(int i=0;i<len - 1;i++){
				bw.write("测试行数\n");
			}
			bw.write("测试行数");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bw != null){
				try {
					bw.close() ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
