
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

// @author yamil
public class ArbolBinario {

    Nodo raiz;
    JTextArea area;

    public ArbolBinario(JTextArea a) {
        area = a;
    }

    public void insercion(String esp, String ing) {
        Nodo p = new Nodo(esp.toUpperCase(), ing.toUpperCase());
        try {
            // Verificar si la palabra ya existe en el archivo
            FileReader fileReader = new FileReader("palabras");
            BufferedReader lector = new BufferedReader(fileReader);
            String entrada = lector.readLine();
            while (entrada != null) {
                String arreglo[] = entrada.split("/");
                if (arreglo[0].equals(esp)) {
                    SwingUtilities.invokeLater(() -> {
                        area.setText("La palabra ya existe en el diccionario.");
                    });
                    lector.close();
                    fileReader.close();
                    return; // Salir del método si la palabra ya existe
                }
                entrada = lector.readLine();
            }
            lector.close();
            fileReader.close();
            if (raiz == null) {
                raiz = p;
            } else {
                Nodo x = raiz;
                Nodo anterior = null;
                while (x != null) {
                    anterior = x;
                    if (esp.compareTo(x.español) < 0) {
                        x = x.izq;
                    } else {
                        x = x.der;
                    }
                }
                if (esp.compareTo(anterior.español) < 0) {
                    anterior.izq = p;
                } else {
                    anterior.der = p;
                }
            }
            FileOutputStream archivo;
            PrintWriter escritor;

            archivo = new FileOutputStream("palabras", true);
            escritor = new PrintWriter(archivo);

            String palabras = esp + "/" + ing + "\n";

            escritor.write(palabras);

            escritor.close();
            archivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArbolBinario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArbolBinario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String buscar(Nodo x, String esp) {
        if (x != null) {
            if (x.español.compareTo(esp) == 0) {
                return x.ingles;
            } else if (esp.compareTo(x.español) < 0) {
                return buscar(x.izq, esp);
            } else {
                return buscar(x.der, esp);
            }
        }
        return "No existe";
    }

    public void reporte(Nodo x) {
        if (x != null) {
            reporte(x.izq);
            area.append(x.español + " ==> " + x.ingles + "\t\n");
            reporte(x.der);
        }
    }

    public void insercion2(String esp, String ing) {
        Nodo p = new Nodo(esp.toUpperCase(), ing);

        if (raiz == null) {
            raiz = p;
        } else {
            Nodo x = raiz;
            Nodo anterior = null;
            while (x != null) {
                anterior = x;
                if (esp.compareTo(x.español) < 0) {
                    x = x.izq;
                } else {
                    x = x.der;
                }
            }
            if (esp.compareTo(anterior.español) < 0) {
                anterior.izq = p;
            } else {
                anterior.der = p;
            }
        }
    }
}
