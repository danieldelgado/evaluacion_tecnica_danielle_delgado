package bcp.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
public class Reto01 {
	@Test
	public void fibonacci() {
		int[] datos = { 89, 5, 21, 7, 144, 7, 34, 233, 55 };
		List<Integer> list = listFin(240);
		List<Integer> s = new ArrayList<>();
		for (int i : datos) {
			for (int ia : list) {
				if (i == ia) {
					s.add(i);
					break;
				}
			}
		}
		System.out.println(s);
		s.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 < o2) {
					return -1;
				} else if (o1 > o2) {
					return 1;
				}
				return 0;
			}
		});
		System.out.println("fibonachi ordenado");
		System.out.println(s);
	}
	@Test
	public void fibonacci2() {
		System.out.println(listFin(240));
	}
	List<Integer> listFin(int l) {
		List<Integer> s = new ArrayList<>();
		int a = 0;
		int temp = 0;
		int i = 1;
		s.add(a);
		s.add(i);
		while (i < l) {
			temp = i;
			i = a + i;
			a = temp;
			s.add(i);
		}
		return s;
	}

}
