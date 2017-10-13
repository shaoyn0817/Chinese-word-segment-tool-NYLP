package NYLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CRFPoser {
	public static void main(String[] args) throws IOException {
		CRFPoser a = new CRFPoser();
		a.CRFpos();
	}

	public void CRFpos() throws IOException {

		String cmd = "cmd.exe /c crf_test.exe -m model2 fenci.data ";
		File workDir = new File("D://CRF++//NYLP//");
		Process p = null;
		p = Runtime.getRuntime().exec(cmd, null, workDir);
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		String str = "";
		while ((line = in.readLine()) != null) {
			str += line + "\r\n";
			//System.out.println(line);
		}
		String[] temp = str.split("\r\n");
		//System.out.println(temp.length);
		for (int i = 0; i < temp.length;) {

			String pp[] = temp[i].split("	");
			if (pp[3].equals("O")) {
				System.out.print(pp[0] + "/" + pp[1] + ", ");
				i++;
			} else if (pp[3].equals("B")) {
				for (int l = i + 1; l < temp.length;) {
					String tt[] = temp[l].split("	");
					// System.out.println();
					// System.out.println(temp[l]);
					if (tt[3].equals("I")) {
						l++;
					} else if (tt[3].equals("O")) {
						String com = "";
						for (int w = i; w < l; w++) {
							String[] linshi = temp[w].split("	");
							com += linshi[0];
						}
						System.out.print(com + " /nt, ");
						i = l;
						break;
					}
				}
			}
		}
	}
}
