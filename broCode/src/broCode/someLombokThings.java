package broCode;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

public class someLombokThings {

}
@Getter
@Setter @Accessors(chain = true)
@ToString(includeFieldNames = false  , onlyExplicitlyIncluded = true , callSuper =  true)
@EqualsAndHashCode(callSuper = false , onlyExplicitlyIncluded = true)
@AllArgsConstructor
@RequiredArgsConstructor
class Cat extends Animal{
	@Accessors(fluent =  true)
	@ToString.Include
	@EqualsAndHashCode.Include
	 private final String name ;
	private String something ;
	@lombok.experimental.Tolerate
	public int getAge(int val) {
		return age + val ; 
	}
 private  final int age  ;
	public static void meow(String something , String anotherthing) {
		System.out.println("meow" + something + anotherthing);
	}
	
	
}

@ToString(includeFieldNames = false)
@Data
class Animal {
	String speicies ; 
	 
}
@Data(staticConstructor =  "of")
class SomeShit {
	@NonNull
	private Integer dataE ; 
	
}
@SuperBuilder(builderMethodName = "newActor" ,setterPrefix = "seet" )
class Actor extends Person{

	@Builder.Default  
	 int age =3 ; 
	 String name ; 
	 String role;
	 @Singular
	 @Getter
		private List<Integer> numsIntegers  ;
	
}
@SuperBuilder
	class Person { 
		String something ; 
	}

 
