package Pokemon;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by 46465442z on 12/10/15.
 */

    // Para generar las clases abrimos un terminal y nos colocamos en carpeta donde se encuentra el ".xsd" mediante el comando cd
    // Después ejecutamos el siguiente comando (sin las comillas): "xjc nombreDeTuXsd.xsd"

public class ControladorPokemons{

    private static String ruta = "/home/sergi/IdeaProjects/BindingJAXB/src/Pokemon/pokemons.xml";
    private static int opcio = 1;
    private static File fitxerXML = new File(ruta);    //creem el file que apunta a la ruta del arxiu XML
    private static Scanner teclat = new Scanner(System.in);

    public static void main(String[] args){

        while (opcio != 0){
            System.out.println("---------------------------------");
            System.out.println("------------ Pokedex ------------");
            System.out.println("---------------------------------");
            System.out.println("1 - Motrar pokemons");
            System.out.println("2 - Afegir pokemon");

            opcio = teclat.nextInt();

            switch (opcio){

                case 1:
                    System.out.println("Carregant Pokemons");
                    mostrarPokemons();
                break;

                case 2:
                    afegirPokemon();
                break;

                default:
                    System.out.println("Ha seleccionat una opcio incorrecta");
                break;
            }
        }
    }

    public static void mostrarPokemons(){

        System.out.println(""); //Linea d'espai estetica

        try{
            JAXBContext context = JAXBContext.newInstance(Pokedex.class);   //Fem el context
            Unmarshaller UMS = context.createUnmarshaller();    //Creem el unmarshaller en el nostre context
            Pokedex pokedex = (Pokedex) UMS.unmarshal(fitxerXML);  //I fem el nostre "pokedex" apuntant al fitxerXML

            System.out.println("POKEMONS: " + pokedex.getPokemon().size()); //Mostem el nombre de Pokemons
            System.out.println("-----------------");

            for (int iterador = 0; iterador < pokedex.getPokemon().size(); iterador++){ //Fem un for per mostrar cada Pokemon amb el seu nom corresponent
                System.out.println(iterador + 1 + " -  " + pokedex.getPokemon().get(iterador).getNombre().getValue());
            }
            System.out.println(""); //Linea d'espai estetica
        }
        catch (JAXBException e){    // Controlem les posibles exepcions
            e.printStackTrace();
        }
    }

    public static void afegirPokemon(){

        try{

            // Demanem el nom, el tipus, la vida i el atac del pokemon
            System.out.println("Introdeuix la ruta del arxiu XML");
                ruta = teclat.next();

            fitxerXML = new File(ruta); //Redefinim el file amb la nova ruta

            System.out.println("Escriu el nom del Pokemon");
                String nom = teclat.next();

            System.out.println("Escriu el tipus del Pokemon");
                String tipus = teclat.next();

            System.out.println("Escriu els HP del Pokemon");
                BigInteger vida = teclat.nextBigInteger();

            System.out.println("Escribe el atac 1 del Pokemon");
                String atac = teclat.next();

            // Creem un nou pokemon i nom

            Pokemon nouPokemon = new Pokemon();
            Nombre nomPokemon = new Nombre();

            nomPokemon.setValue(nom);           //Li asignem a la clase nom el valor introduit pel usuari
            nomPokemon.setClasse(tipus);        //Li asignem el tipus
            nouPokemon.setNombre(nomPokemon);   //li asignem el nom
            nouPokemon.setPV(vida);             //Li asignem la vida
            nouPokemon.setAtaque1(atac);        //Li asignem el atac

            JAXBContext context = JAXBContext.newInstance(Pokedex.class); //Fem el context
            Unmarshaller UMS = context.createUnmarshaller();    //Creem el unmarshaller en el nostre context
            Pokedex pokedex = (Pokedex) UMS.unmarshal(fitxerXML); //I fem el nostre "pokedex" apuntant al fitxerXML

            pokedex.getPokemon().add(nouPokemon);   //afegim el nou pokemon
            guardarPokemon(pokedex);    //Ho escribim tot
        }
        catch (JAXBException e){    //Controlem les posibles exepcions
            e.printStackTrace();
        }
    }

    public static void guardarPokemon(Pokedex pokedex){

        try{
            JAXBContext context = JAXBContext.newInstance(Pokedex.class);   //fem el context per al marshaller
            Marshaller MS = context.createMarshaller();                     //creem el marshaller en el nostre context
            MS.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);         //ho preparem per escriure
            MS.marshal(pokedex, fitxerXML);                                 //escribim al fitxer XML
        }
        catch (JAXBException e){    //Controlem les posibles excepcions
            e.printStackTrace();
        }
    }
}
