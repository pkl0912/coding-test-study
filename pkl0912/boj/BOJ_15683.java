package pkl0912.boj;

import java.io.*;
import java.util.*;

public class BOJ_15683 {
	public static int N, M;
	public static int[][] map;
	public static int[][] copyMap;
	public static int[] output;
	public static ArrayList<CCTV> cctvList;
	public static int[] dx = {-1, 0, 1, 0}; 
	public static int[] dy = {0, 1, 0, -1};
	public static int answer = Integer.MAX_VALUE;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][M];
		cctvList = new ArrayList<>();

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				map[i][j] = sc.nextInt();

				if(map[i][j] != 0 &&  map[i][j] != 6) {
					cctvList.add(new CCTV(map[i][j], i, j));
				}
			}
		}

		output = new int[cctvList.size()]; 
		permutation(0, cctvList.size());

		System.out.println(answer);
	}

	public static void permutation(int depth, int r) {
		if(depth == r) {
			copyMap = new int[N][M];
			for(int i = 0; i < map.length; i++) {
				System.arraycopy(map[i], 0, copyMap[i], 0, map[i].length);
			}
						
			for(int i = 0; i < cctvList.size(); i++) {
				direction(cctvList.get(i), output[i]);
			}
			
			getBlindSpot();

			return;
		}
		
		for(int i = 0; i < 4; i++) {
			output[depth] = i;
			permutation(depth+1, r);
		}
	}

	public static void direction(CCTV cctv, int d) {
		int cctvNum = cctv.num;

		if(cctvNum == 1) {
			if(d == 0) watch(cctv, 0); // 상 
			else if(d == 1) watch(cctv, 1); // 우 
			else if(d == 2) watch(cctv, 2); // 하  
			else if(d == 3) watch(cctv, 3); // 좌 
		} else if(cctvNum == 2) {
			if(d == 0 || d == 2) {
				watch(cctv, 0); watch(cctv, 2); // 상하 
			} else {
				watch(cctv, 1); watch(cctv, 3); // 좌우 
			}
		} else if(cctvNum == 3) {
			if(d == 0) {
				watch(cctv, 0); // 상우 
				watch(cctv, 1);
			} else if(d == 1) { 
				watch(cctv, 1); // 우하 
				watch(cctv, 2);
			} else if(d == 2) { 
				watch(cctv, 2); // 하좌 
				watch(cctv, 3);
			} else if(d == 3) { 
				watch(cctv, 0); // 좌상 
				watch(cctv, 3);
			}
		} else if(cctvNum == 4) {
			if(d == 0) {
				watch(cctv, 0); // 좌상우 
				watch(cctv, 1);
				watch(cctv, 3);
			} else if(d == 1) {
				watch(cctv, 0); // 상우하 
				watch(cctv, 1);
				watch(cctv, 2);
			} else if(d == 2) {
				watch(cctv, 1); // 좌하우 
				watch(cctv, 2);
				watch(cctv, 3);
			} else if(d == 3) {
				watch(cctv, 0); // 상좌하 
				watch(cctv, 2);
				watch(cctv, 3);
			}
		} else if(cctvNum == 5) { // 상우하좌
			watch(cctv, 0);
			watch(cctv, 1);
			watch(cctv, 2);
			watch(cctv, 3);
		}
	}
	
	public static void watch(CCTV cctv, int d) {
		Queue<CCTV> queue = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];

		queue.add(cctv);
		visited[cctv.x][cctv.y] = true;

		while(!queue.isEmpty()) {
			int nx = queue.peek().x + dx[d];
			int ny = queue.poll().y + dy[d];

			if(nx < 0 || nx >= N || ny < 0 || ny >= M || copyMap[nx][ny] == 6) { 
				break;
			}

			if(copyMap[nx][ny] == 0) { 
				copyMap[nx][ny] = -1; 
				queue.add(new CCTV(cctv.num, nx, ny));
			} else { 
				queue.add(new CCTV(cctv.num, nx, ny)); 
			}
		}
	}
	
	public static void getBlindSpot() {
		int cnt = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(copyMap[i][j] == 0) {
					cnt++;
				}
			}
		}
		answer = Math.min(answer, cnt);
	}


}

class CCTV {
	int num;
	int x;
	int y;

	CCTV(int num, int x, int y) {
		this.num = num;
		this.x = x;
		this.y = y;
	}
}
