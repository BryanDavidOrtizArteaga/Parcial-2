package Bodega;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;

public class InventarioFrame extends JFrame {
    private JTable table;
    private JButton refreshButton, addButton;
    private JTextField idField, nombreField, precioField;

    public InventarioFrame() throws FileNotFoundException {
        setTitle("Inventario de Bodega");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio"}, 0));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 4));

        panelInferior.add(new JLabel("ID:"));
        idField = new JTextField();
        panelInferior.add(idField);

        panelInferior.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panelInferior.add(nombreField);

        panelInferior.add(new JLabel("Precio:"));
        precioField = new JTextField();
        panelInferior.add(precioField);

        addButton = new JButton("Agregar Producto");
        refreshButton = new JButton("Actualizar Tabla");

        panelInferior.add(addButton);
        panelInferior.add(refreshButton);

        add(panelInferior, BorderLayout.SOUTH);

        FirebaseSaveObject firebaseSaveObject = new FirebaseSaveObject();
        firebaseSaveObject.retrieveAndShowProducts(table);

        refreshButton.addActionListener(e -> {
            try {
                firebaseSaveObject.retrieveAndShowProducts(table);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        addButton.addActionListener(e -> {
            try {
                long id = Long.parseLong(idField.getText());
                String nombre = nombreField.getText();
                double precio = Double.parseDouble(precioField.getText());

                Item newItem = new Item(id, nombre, precio);
                firebaseSaveObject.saveProduct(newItem);
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");

                firebaseSaveObject.retrieveAndShowProducts(table);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar producto. Verifica los datos.");
            }
        });
    }
}