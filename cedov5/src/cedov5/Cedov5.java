package cedov5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Класс, реализующий систему управления доставкой еды.
 * Весь интерфейс переведён на русский язык.
 * 
 * @author студент
 */
public class Cedov5 extends JFrame {

    private JTextField tfOrderId, tfCustomer, tfFoodItem, tfQuantity, tfAddress, tfDeliveryBoy, tfSearch;
    private DefaultTableModel model;
    private JTable table;
    private ArrayList<FoodOrder> orderList = new ArrayList<>();

    public Cedov5() {
        setTitle("🍽️ Система управления доставкой еды");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("🍽️ Система управления доставкой еды", JLabel.CENTER);
        heading.setFont(new Font("Verdana", Font.BOLD, 24));
        heading.setOpaque(true);
        heading.setBackground(new Color(0, 153, 76));
        heading.setForeground(Color.white);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Введите детали заказа"));
        form.setBackground(new Color(240, 255, 250));
        tfOrderId = new JTextField();
        tfCustomer = new JTextField();
        tfFoodItem = new JTextField();
        tfQuantity = new JTextField();
        tfAddress = new JTextField();
        tfDeliveryBoy = new JTextField();

        form.add(new JLabel("ID заказа:"));
        form.add(tfOrderId);
        form.add(new JLabel("Имя клиента:"));
        form.add(tfCustomer);
        form.add(new JLabel("Блюдо:"));
        form.add(tfFoodItem);
        form.add(new JLabel("Количество:"));
        form.add(tfQuantity);
        form.add(new JLabel("Адрес:"));
        form.add(tfAddress);
        form.add(new JLabel("Курьер:"));
        form.add(tfDeliveryBoy);

        JButton btnAdd = new JButton("➕ Добавить заказ");
        JButton btnSearch = new JButton("🔍 Поиск клиента");
        styleButton(btnAdd);
        styleButton(btnSearch);
        form.add(btnAdd);
        form.add(btnSearch);
        add(form, BorderLayout.WEST);

        model = new DefaultTableModel(new String[]{"ID заказа", "Клиент", "Блюдо", "Кол-во", "Адрес", "Курьер"}, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(new Color(240, 255, 250));
        tfSearch = new JTextField(20);
        JButton btnDelete = new JButton("🗑️ Удалить выбранное");
        JButton btnShowAll = new JButton("📄 Показать все");
        styleButton(btnDelete);
        styleButton(btnShowAll);
        bottom.add(new JLabel("Поиск клиента:"));
        bottom.add(tfSearch);
        bottom.add(btnShowAll);
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addOrder());
        btnShowAll.addActionListener(e -> showAllOrders());
        btnSearch.addActionListener(e -> searchOrders());
        btnDelete.addActionListener(e -> deleteSelectedOrder());

        // Примеры данных (переведены на русский)
        orderList.add(new FoodOrder("F001", "Джон", "Бургер", "2", "123 Главная ул", "Алекс"));
        orderList.add(new FoodOrder("F002", "Эмили", "Пицца", "1", "456 Вязовая ул", "Брайан"));
        showAllOrders();
    }

    private void addOrder() {
        String id = tfOrderId.getText().trim();
        String cust = tfCustomer.getText().trim();
        String food = tfFoodItem.getText().trim();
        String qty = tfQuantity.getText().trim();
        String addr = tfAddress.getText().trim();
        String boy = tfDeliveryBoy.getText().trim();

        if (id.isEmpty() || cust.isEmpty() || food.isEmpty() || qty.isEmpty() || addr.isEmpty() || boy.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, заполните все поля!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }

        orderList.add(new FoodOrder(id, cust, food, qty, addr, boy));
        showAllOrders();
        tfOrderId.setText(""); tfCustomer.setText(""); tfFoodItem.setText("");
        tfQuantity.setText(""); tfAddress.setText(""); tfDeliveryBoy.setText("");
    }

    private void showAllOrders() {
        model.setRowCount(0);
        for (FoodOrder o : orderList) {
            model.addRow(new Object[]{o.orderId, o.customerName, o.foodItem, o.quantity, o.address, o.deliveryBoy});
        }
    }

    private void searchOrders() {
        String name = tfSearch.getText().trim().toLowerCase();
        model.setRowCount(0);
        for (FoodOrder o : orderList) {
            if (o.customerName.toLowerCase().contains(name)) {
                model.addRow(new Object[]{o.orderId, o.customerName, o.foodItem, o.quantity, o.address, o.deliveryBoy});
            }
        }
    }

    private void deleteSelectedOrder() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Выберите строку для удаления.");
            return;
        }
        String id = model.getValueAt(row, 0).toString();
        orderList.removeIf(o -> o.orderId.equals(id));
        showAllOrders();
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 204, 153));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cedov5().setVisible(true));
    }
}

/**
 * Вспомогательный класс для хранения данных заказа.
 */
class FoodOrder {
    String orderId, customerName, foodItem, quantity, address, deliveryBoy;

    FoodOrder(String orderId, String customerName, String foodItem, String quantity, String address, String deliveryBoy) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.address = address;
        this.deliveryBoy = deliveryBoy;
    }
}