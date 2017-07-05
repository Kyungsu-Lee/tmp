package genetic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

import genetic.csv.*;
import genetic.data.*;
import genetic.mutation.*;
import genetic.gene.*;

public class Main
{
	static DistanceTable distanceTable;

	private static class cmp implements Comparator<Gene>
	{
		public int compare(Gene g1, Gene g2)
		{
			Integer i1 = new Integer(g1.getTotalNextDistance());
			return i1.compareTo(new Integer(g2.getTotalNextDistance()));
		}
	}

	public static void main(String[] args) throws Exception
	{
		BufferedWriter out = new BufferedWriter(new FileWriter("out.txt"));
		ClassSystem system = new ClassSystem(args[0]);
		system.selectSemester(2013, 1);


		for(int count=0; count< 20; count++)
		{
			Gene gene = ClassManager.getInstance().makeGene();

			for(int i=1; i < 100; i++)
			{
				GreedyAdaptor rA = new GreedyAdaptor();
				rA.setGene(gene);
				gene= rA.mutate(100);
				String str = (i + "," + gene.getTotalNextDistance()+ ",");
				System.out.println(str);
				out.write(str); out.newLine();
			}
			System.out.println("====");

			for(ClassGene _class : gene.getClassGene())
			{
				String str = (_class + "\t" + _class.getClassRoom().getName());
				System.out.println(str);
				out.write(str); out.newLine();
			}
		}

		out.close();
	}
}
