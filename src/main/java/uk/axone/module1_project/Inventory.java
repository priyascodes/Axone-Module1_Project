package uk.axone.module1_project;

import java.util.*;



public class Inventory implements Comparable<Item> {

    //Declarations of collections used
    public Map<Item, Integer> stock = new HashMap<>();

    private List<Item> listOfItems;
    private Map<Item, Integer> listOfItemsAndCodes;


    //Constructor declaration
    public Inventory() throws InvalidDataException {
        this.load();
    }

    //private Method load() to populate the data
    private void load() throws InvalidDataException {

        //initialisation of collections
        listOfItems = new ArrayList<>();
        listOfItemsAndCodes = new HashMap<>();

//Reading Excel Sheet for further attributes
        ExcelReader excelReader = new ExcelReader("InventoryData.xlsx", "Sheet1");
        //System.out.println(excelReader.getCellValue(0, 0));

        int rows = excelReader.getRowCount();
        int cols = excelReader.getColumnCount();


//simple printout of the contents of the Excel sheet
        System.out.println("Excel Sheet Print out");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                System.out.print(excelReader.getCellValue(i, j) + "     ");
            }
            System.out.println();
        }

        System.out.println("*****************************");


        // iter through the excel sheet and get an initial Map of Items and Item Codes

        for (int i = 1; i < rows; i++) {
            String excelItemCode = excelReader.getCellValue(i, 0);
            Integer intExcelItemCode = Integer.parseInt(excelItemCode);

            String excelItemName = excelReader.getCellValue(i, 1);

            listOfItemsAndCodes.put(new Item(excelItemName, intExcelItemCode), intExcelItemCode);

        }


        //****Separate for loop *************
        //Getting a list of Items objects alone

        //   System.out.println(listOfItem.getItemName() + "  " + listOfItem.getItemCode() + "   " + listOfItem.getItemPrice());
        listOfItems.addAll(listOfItemsAndCodes.keySet());


        ///******Separate for loop ******************
        //Print out of the list of Items alone
        System.out.println("listOfItems Contents: - Arrays list of items objects");

        for (final Item item2 : listOfItems) {
            System.out.println(item2.getItemName());
        }


        //****Populating the rest of the data ****************
        for (int r = 1; r < rows; r++) {

            final String excelItemCode1 = excelReader.getCellValue(r, 0);
            final Integer intExcelItemCode1 = Integer.parseInt(excelItemCode1);

            for (Item item3 : listOfItemsAndCodes.keySet()) {

                if (intExcelItemCode1.equals(listOfItemsAndCodes.get(item3))) {

                    final String excelItemPrice = excelReader.getCellValue(r, 4);
                    final Integer intExcelItemPrice = Integer.parseInt(excelItemPrice);
                    final String excelItemDescription = excelReader.getCellValue(r, 2);

                    item3.setItemPrice(intExcelItemPrice);
                    item3.setItemDescription(excelItemDescription);

                    final String excelItemQuantity = excelReader.getCellValue(r, 3);
                    final Integer intExcelItemQuantity = Integer.parseInt(excelItemQuantity);
                    stock.put(item3, intExcelItemQuantity);

                }
            }
        }

        //Printing list of Items and their attributes after population from Excel file
        System.out.println("listOfItems and their attributes:");
        for (final Item item4 : listOfItems) {
            System.out.println(item4.getItemName() + "  " + item4.getItemCode() + "   " + item4.getItemDescription() + "    " + item4.getItemPrice());
        }

        //Printing Stock contents to be used by other classes
        System.out.println("Stock Contents:");
        for (final Map.Entry<Item, Integer> stockItem : stock.entrySet()) {
            System.out.println(stockItem.getKey().getItemName() + "   " + stockItem.getValue());
        }


    }   //END OF LOAD METHOD


    //Method to check for item

    public void checkItemFound(final Item checkItem) throws ItemNotFoundException {

        if (stock.containsKey(checkItem)) {
            System.out.println("ITem found");
        } else {
            throw new ItemNotFoundException("Not found");
        }
    }  //END OF CHECK METHOD


    //Method reduceStock()

    public boolean reduceStock(final Item it, final Integer quantity) {

        System.out.println(it.getItemName());

        // check if item is found - exists
        try {
            checkItemFound(it);
        } catch (ItemNotFoundException inf) {
            System.out.println(inf.getMessage());
        }

        final Integer oldQuantity = stock.get(it);
        final Integer newQuantity;


        if (quantity < 0) {

            System.out.println("Invalid Quantity entered");
            return false;

        } else if (quantity <= oldQuantity) {
            newQuantity = oldQuantity - quantity;
            if (newQuantity == 0) {
                System.out.println("Item" + "  " + it.getItemName() + "  out of stock");
                stock.replace(it, oldQuantity, newQuantity);
                return true;
            } else {
                stock.replace(it, oldQuantity, newQuantity);
                return true;
            }

        } else if (quantity > oldQuantity) {

            System.out.println("Item quantity to be reduced is larger than in stock. Invalid.");
            return false;
        }
        return false;
    }  //END OF REDUCE METHOD


    //Override method from Comparable Class

    @Override
    public int compareTo(Item o) {
        return 0;
    }

}    // END OF CLASS










/*
public class Inventory implements Comparable<Item> {

    public Map<Item, Integer> stock = new HashMap<Item, Integer>();

    public List<Item> listOfItems;

    public Inventory() throws InvalidDataException {
        this.load();
    }

    public void load() throws InvalidDataException {

        List<Item> listOfItems = new ArrayList<Item>();
// Manually instantiated Items - Hardcoded
        Item nokia = new Item("nokia", 878);
        nokia.setItemPrice(450);
        nokia.setItemDescription("Oldy but goody");
        listOfItems.add(nokia);

        Item iphone12 = new Item("iphone12", 100);
        iphone12.setItemDescription("Powerful and expensive");
        iphone12.setItemPrice(1000);
        listOfItems.add(iphone12);

        Item samsungGalaxy = new Item("samsungGalaxy", 102);
        samsungGalaxy.setItemDescription("Android versatile");
        samsungGalaxy.setItemPrice(600);
        listOfItems.add(samsungGalaxy);

        Item onePlus = new Item("onePlus", 103);
        onePlus.setItemDescription("Sturdy and robust");
        onePlus.setItemPrice(600);
        listOfItems.add(onePlus);


        stock.put(iphone12, 20);
        stock.put(nokia, 30);
        stock.put(samsungGalaxy, 40);
        stock.put(onePlus, 43);
        System.out.println("In load method");

//printout contents of stock
        for (Map.Entry<Item, Integer> entry : stock.entrySet()) {
            System.out.println(entry.getKey().getItemName());
            System.out.println(entry.getValue());
        }

//printout contents of listOfItems

        for (Item item : listOfItems) {
            System.out.println(item.getItemName());

        }
    }


//method to check for item

    public void checkItemFound(Item checkItem) throws ItemNotFoundException {

        if (stock.containsKey(checkItem)) {
            System.out.println("ITem found");
        } else {
            throw new ItemNotFoundException("Not found");
        }
    }


//method reduceStock()

    public boolean reduceStock(Item it, Integer quantity) throws ItemNotFoundException {

        System.out.println(it.getItemName());

        // check if item is found - exists
        try {
            checkItemFound(it);
        } catch (ItemNotFoundException inf) {
            System.out.println(inf.getMessage());
        }

        Integer oldQuantity = stock.get(it);
        Integer newQuantity;


        if (quantity < 0) {

            System.out.println("Invalid Quantity entered");
            return false;

        } else if (quantity <= oldQuantity && quantity >= 0) {
            newQuantity = oldQuantity - quantity;
            if (newQuantity == 0) {
                System.out.println("Item" + "  " + it.getItemName() + "  out of stock");
                stock.replace(it, oldQuantity, newQuantity);
                return true;
            } else {
                stock.replace(it, oldQuantity, newQuantity);
                return true;
            }

        } else if (quantity > oldQuantity) {

            System.out.println("Item quantity larger than in stock. Invalid.");
            return false;
        }
        return false;
    }

    @Override
    public int compareTo(Item o) {
        return 0;
    }


}


*/


