package com.johnfnash.learn.guava;

import java.util.ArrayList;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ImmutableCollectionTest {

	@Test
	public void testImmutableList() {
		String[] strs = new String[] {"wyp", "good"};
		ImmutableList<String[]> list = ImmutableList.of(strs);
		for (String[] strings : list) {
			for (String str : strings) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		
		strs[1] = "good 2";
		for (String[] strings : list) {
			for (String str : strings) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
	}
	
	@Test
	public void testImmutableList2() {
		ArrayList<String> list = Lists.newArrayList("a","b","c");
		ImmutableList<String> imList = ImmutableList.copyOf(list);
        System.out.println(imList);
        list.add("d");
        System.out.println("修改原始集合之后，imList的内容为："+imList);
	}
	
}
