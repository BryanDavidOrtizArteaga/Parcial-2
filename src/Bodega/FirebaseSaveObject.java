package Bodega;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FirebaseSaveObject {
    private FirebaseDatabase firebaseDatabase;

    public FirebaseSaveObject() throws FileNotFoundException {
        initFirebase();
    }

    private void initFirebase() throws FileNotFoundException {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://bodega-5f1a1-default-rtdb.firebaseio.com/")
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\USUARIO\\Documents\\NetBeansProjects\\Parcial\\bodega-5f1a1-firebase-adminsdk-fbsvc-41fc351d1f.json\\")))
                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            System.out.println("Firebase inicializado correctamente.");
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        System.out.println("Conexión a Firebase exitosa...");
    }

    public void saveProduct(Item item) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("bodega/productos");
        databaseReference.child(String.valueOf(item.getId())).setValue(item, (error, ref) -> {
            if (error != null) {
                System.out.println("Error al guardar: " + error.getMessage());
            } else {
                System.out.println("Producto guardado exitosamente.");
            }
        });
    }

    public void retrieveAndShowProducts(JTable table) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("bodega/productos");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Item> items = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    items.add(item);
                }

                Collections.sort(items, (a, b) -> Long.compare(a.getId(), b.getId()));

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                for (Item item : items) {
                    model.addRow(new Object[]{item.getId(), item.getNombre(), item.getPrecio()});
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al recuperar datos: " + databaseError.getMessage());
            }
        });
    }
}