    import java.util.ArrayList;
    import java.util.List;

    public class Main {
        public static void main(String[] args) {
            List<Student> students = List.of(
                new Student("Michell", 21, Gender.FEMALE),
                new Student("Ander", 21, Gender.MALE),
                new Student("Son", 21, Gender.MALE),
                new Student("Magola", 21, Gender.FEMALE)
            );


            System.out.println("\n \n Estudiantes Hombres");
            for(Student student : students){
                if(Gender.MALE.equals(student.gender)){
                    System.out.println(student);
                }
            }

            System.out.println("\n \n Estudiantes Mujeres");
            List<Student> Females = new ArrayList<>();
            for(Student student : students){
                if(Gender.FEMALE.equals(student.gender)){
                    Females.add(student);
                }
            }
            for(Student student : Females){
                System.out.println(student);
            }


            List<Person> personas = new ArrayList<>();
            for(Student studentImp : students){
                Person persona = new Person(studentImp.name, studentImp.age);
                personas.add(persona);
            }

            System.out.println("\n \n Programación imperativa tipo persona");
            for(Person person : personas){
                System.out.println(person);
            }
    }

    static class Person{
    private final String name;
    private final Integer age;
    public Person(String name, Integer age){
        this.name = name;
        this.age = age;
    }
    @Override
    public String toString(){
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
    }
    static class Student{
        private final String name;
        private final Integer age;
        private final Gender gender;

        public Student(String name, Integer age, Main.Gender gender){
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        @java.lang.Override
        public java.lang.String toString(){
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }
    }


    enum Gender{
        FEMALE, MALE;
    }

    }


