import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Backend{
    public static ArrayList<VaccinatedList> data = new ArrayList<>();

    public static boolean load(String filename) throws FileNotFoundException {
        boolean duplicate = false; // false- no duplicate  true- duplicate
        boolean empty = false;
        //parsing a CSV file into Scanner class constructor
        Scanner sc = new Scanner(new File(filename));

        sc.useDelimiter(",|\\n"); //sets the delimiter pattern

        for (int i = 0; i < 6; i++) {
            if (sc.hasNext()){
                sc.next();
            }else{
                empty = true;
                return empty;
            }
        }
        while (sc.hasNext()) {
            int id = Integer.parseInt(sc.next());
            String lastName = sc.next();
            String firstName = sc.next();
            String vaccine = sc.next();
            String date = sc.next();
            String location = sc.next();

            for (VaccinatedList datum : data) {
                if (id == datum.getId()) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                data.add(new VaccinatedList(id, lastName, firstName, vaccine, date, location));
            }
            duplicate = false;
            }
        sc.close();

        return empty;
    }

    public static boolean add(int id, String lastName, String firstName, String vaccine, String date, String location){
        boolean duplicate = false;
        for (VaccinatedList datum : data) {
            if (id == datum.getId()) {
                duplicate = true;

                break;
            }
        }
        if(!duplicate){
            data.add(new VaccinatedList(id, lastName, firstName, vaccine, date, location));
        }
        return duplicate;
    }

    public static void save(String fileName) throws IOException {
        FileWriter wr = new FileWriter(fileName);

        wr.append("ID,Last Name,First Name,Vaccine Type,Vaccination Date,Vaccine Location\n");

        for (VaccinatedList datum : data) {
            wr.append(String.valueOf(datum.getId())).append(",");
            wr.append(datum.getLastName()).append(",");
            wr.append(datum.getFirstName()).append(",");
            wr.append(datum.getVaccine()).append(",");
            wr.append(datum.getDate()).append(",");
            wr.append(datum.getLocation());
            wr.append("\n");
        }

        wr.flush();
        wr.close();
    }

    public static JFreeChart bar_visualize() throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<String> vaccines = new ArrayList<String>();

        for(int i = 0;i<data.size();i++){
            vaccines.add(data.get(i).getVaccine());
        }

        Set<String> distinct_vaccines = new HashSet<>(vaccines);

        for (String s: distinct_vaccines) {
            dataset.setValue(Collections.frequency(vaccines, s),"Number of Vaccines Administered", s);
        }

        JFreeChart chart = ChartFactory.createBarChart("Bar Chart", "Vaccine Type", "Number of Vaccines Administered",
                dataset, PlotOrientation.VERTICAL, false, false, false);

        return chart;
    }

    public static JFreeChart pie_visualize() throws IOException {
        DefaultPieDataset dataset=new DefaultPieDataset();

        List<String> locations = new ArrayList<String>();;

        for(int i = 0;i<data.size();i++){
            locations.add(data.get(i).getLocation());
        }

        Set<String> distinct_locations = new HashSet<>(locations);

        for (String s: distinct_locations) {
            dataset.setValue(s,Collections.frequency(locations, s));
        }

        JFreeChart chart = ChartFactory.createPieChart("Pie Chart", dataset, true, true, false);

        return chart;
    }

    public static Object[][] convert2dArray() {
        Object[][] arr = new Object[data.size()][6];
        for (int i = 0; i < data.size(); i++) {
            arr[i][0] = data.get(i).getId();
            arr[i][1] = data.get(i).getLastName();
            arr[i][2] = data.get(i).getFirstName();
            arr[i][3] = data.get(i).getVaccine();
            arr[i][4] = data.get(i).getDate();
            arr[i][5] = data.get(i).getLocation();
        }
        return arr;
    }
}
