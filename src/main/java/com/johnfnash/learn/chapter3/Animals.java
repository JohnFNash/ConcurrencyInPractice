package com.johnfnash.learn.chapter3;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Animals
 * <p/>
 * Thread confinement of local primitive and reference variables
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Animals {
	Ark ark;
	Species species;
	Gender gender;
	
	public int loadTheArk(Collection<Animal> candidates) {
		SortedSet<Animal> animals;
		int numPairs = 0;
		Animal candaidate = null;
		
		// animals confined to method, don't let them escape!
		animals = new TreeSet<Animal>(new SpeciesGenderComparator());
		animals.addAll(candidates);
		for(Animal a : animals) {
			if(candaidate == null || !candaidate.isPotentialMate(a)) {
				candaidate = a;
			} else {
				ark.load(new AnimalPair(candaidate, a));
				++numPairs;
				candaidate = null;
			}
		}
		
		return numPairs;
	}
	
	class Animal {
		Species species;
		Gender gender;
		
		public boolean isPotentialMate(Animal other) {
			return species == other.species && gender != other.gender;
		}
	}
	
	enum Species {
		AARDVARK, BENGAL_TIGER, CARIBOU, DINGO, ELEPHANT, FROG, GNU, HYENA,
        IGUANA, JAGUAR, KIWI, LEOPARD, MASTADON, NEWT, OCTOPUS,
        PIRANHA, QUETZAL, RHINOCEROS, SALAMANDER, THREE_TOED_SLOTH,
        UNICORN, VIPER, WEREWOLF, XANTHUS_HUMMINBIRD, YAK, ZEBRA
	}
	
	enum Gender {
        MALE, FEMALE
    }
	
	class AnimalPair {
		private final Animal one, two;
		
		public AnimalPair(Animal one, Animal two) {
			this.one = one;
			this.two = two;
		}

		public Animal getOne() {
			return one;
		}

		public Animal getTwo() {
			return two;
		}
	}
	
	class SpeciesGenderComparator implements Comparator<Animal> {
		@Override
		public int compare(Animal one, Animal two) {
			int specialsCompare = one.species.compareTo(two.species);
			return (specialsCompare != 0)
				? specialsCompare : one.gender.compareTo(two.gender);
		}
	}
	
	class Ark {
		private final Set<AnimalPair> loadedAnimals = new HashSet<AnimalPair>();
		
		public void load(AnimalPair pair) {
			loadedAnimals.add(pair);
		}
	}
	
}
