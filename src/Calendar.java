import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Calendar {

    public static final String[] TASK_LIST  = new String[]{"Подъём в 07:00", "Зарядка", "Правильное питание", "Тренировка", "Иностранный язык", "Отбой в 00:00"};
    public static final int      TASK_COUNT = TASK_LIST.length;
    public static       int      weekOffset = 0;

    public static void updateDayTotal(LocalDate day, Member parent) {
        Sprite[] tasks = getDayTasks(day, parent);
        Text     total = getDayTotal(day, parent);
        int      count = 0;
        for (Sprite task : tasks) if ((task.extra&2) == 2) count++;
        total.text = ""+count;
    }

    public static void updateWeekTotal(LocalDate day, Member parent) {
        int    count      = 0;
        Text[] totalArray = getWeekTotalArray(day, parent);
        for (Text total : totalArray) if (total != null) count += Integer.parseInt(total.text);
        getWeekTotal(parent).text = ""+count;
    }

    public static void updateDayColor(LocalDate day, Member parent) {
        Sprite[] tasks    = getDayTasks(day, parent);
        Text     dayTotal = getDayTotal(day, parent);
        if (Integer.parseInt(dayTotal.text) == TASK_COUNT) {
            for (Sprite task : tasks) task.setColors(Colors.green(2), Colors.green(3), Colors.green(4));
            return;
        }
        for (Sprite task : tasks) {
            switch (task.extra) {
                case 0 -> task.setColors(Colors.blue2(1), Colors.blue2(2), Colors.blue2(3));
                case 1 -> task.setColors(Colors.blue2(2), Colors.blue2(3), Colors.blue2(4));
                case 2 -> task.setColors(Colors.blue2(5), Colors.blue2(6), Colors.blue2(7));
                case 3 -> task.setColors(Colors.blue2(6), Colors.blue2(7), Colors.blue2(8));
                default -> throw new IllegalStateException("Unexpected value: "+task.extra);
            }
        }
    }

    public static String getTaskId(int n) {
        if (n > 999) return ""+n;
        else if (n > 99) return "0"+n;
        else if (n > 9) return "00"+n;
        else return "000"+n;
    }

    public static String getDayStr(LocalDate day) {
        return day.format(DateTimeFormatter.ofPattern("ddMMyy"));
    }

    public static Sprite getDayTask(LocalDate day, int taskId, Member parent) {
        return (Sprite) parent.getChild("task"+getDayStr(day)+getTaskId(taskId));
    }

    public static Sprite[] getDayTasks(LocalDate day, Member parent) {
        Sprite[] result = new Sprite[TASK_COUNT];
        for (int i = 0; i < TASK_COUNT; i++) result[i] = (Sprite) parent.getChild("task"+getDayStr(day)+getTaskId(i));
        return result;
    }

    public static Text getDayTotal(LocalDate day, Member parent) {
        return (Text) parent.getChild("total"+getDayStr(day));
    }

    public static Text getWeekTotal(Member parent) {
        return (Text) parent.getChild("weekTotal");
    }

    public static Text[] getWeekTotalArray(LocalDate day, Member parent) {
        Text[] result = new Text[7];
        int    dow    = day.getDayOfWeek().getValue()-1;
        for (int i = 0; i < 7; i++) result[i] = getDayTotal(day.plusDays(i-dow), parent);
        return result;
    }

}
