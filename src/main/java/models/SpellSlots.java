package models;

public class SpellSlots {
    private int level_one_slots = 0;
    private int level_two_slots = 0;
    private int level_three_slots = 0;
    private int level_four_slots = 0;
    private int level_five_slots = 0;
    private int level_six_slots = 0;
    private int level_seven_slots = 0;
    private int level_eight_slots = 0;
    private int level_nine_slots = 0;

    public SpellSlots(){}

    public SpellSlots(int level_one_slots) {
        this.level_one_slots = level_one_slots;
    }

    public boolean hasSlotsAtLevel(int level){
        switch (level){
            case 1:
                return level_one_slots > 0;
            case 2:
                return level_two_slots > 0;
            case 3:
                return level_three_slots > 0;
            case 4:
                return level_four_slots > 0;
            case 5:
                return level_five_slots > 0;
            case 6:
                return level_six_slots > 0;
            case 7:
                return level_seven_slots > 0;
            case 8:
                return level_eight_slots > 0;
            case 9:
                return level_nine_slots > 0;
            default:
                return false;
        }
    }

    public void useSlot(int level){
        switch (level){
            case 1:
                level_one_slots--;
                break;
            case 2:
                level_two_slots--;
                break;
            case 3:
                level_three_slots--;
                break;
            case 4:
                level_four_slots--;
                break;
            case 5:
                level_five_slots--;
                break;
            case 6:
                level_six_slots--;
                break;
            case 7:
                level_seven_slots--;
                break;
            case 8:
                level_eight_slots--;
                break;
            case 9:
                level_nine_slots--;
        }
    }

    public SpellSlots(int level_one_slots, int level_two_slots, int level_three_slots,
                      int level_four_slots, int level_five_slots, int level_six_slots,
                      int level_seven_slots, int level_eight_slots, int level_nine_slots) {
        this.level_one_slots = level_one_slots;
        this.level_two_slots = level_two_slots;
        this.level_three_slots = level_three_slots;
        this.level_four_slots = level_four_slots;
        this.level_five_slots = level_five_slots;
        this.level_six_slots = level_six_slots;
        this.level_seven_slots = level_seven_slots;
        this.level_eight_slots = level_eight_slots;
        this.level_nine_slots = level_nine_slots;
    }

    public SpellSlots(int level_one_slots, int level_two_slots, int level_three_slots,
                      int level_four_slots, int level_five_slots, int level_six_slots,
                      int level_seven_slots, int level_eight_slots) {

        this.level_one_slots = level_one_slots;
        this.level_two_slots = level_two_slots;
        this.level_three_slots = level_three_slots;
        this.level_four_slots = level_four_slots;
        this.level_five_slots = level_five_slots;
        this.level_six_slots = level_six_slots;
        this.level_seven_slots = level_seven_slots;
        this.level_eight_slots = level_eight_slots;
    }

    public SpellSlots(int level_one_slots, int level_two_slots, int level_three_slots,
                      int level_four_slots, int level_five_slots, int level_six_slots, int level_seven_slots) {

        this.level_one_slots = level_one_slots;
        this.level_two_slots = level_two_slots;
        this.level_three_slots = level_three_slots;
        this.level_four_slots = level_four_slots;
        this.level_five_slots = level_five_slots;
        this.level_six_slots = level_six_slots;
        this.level_seven_slots = level_seven_slots;
    }

    public SpellSlots(int level_one_slots, int level_two_slots, int level_three_slots,
                      int level_four_slots, int level_five_slots, int level_six_slots) {

        this.level_one_slots = level_one_slots;
        this.level_two_slots = level_two_slots;
        this.level_three_slots = level_three_slots;
        this.level_four_slots = level_four_slots;
        this.level_five_slots = level_five_slots;
        this.level_six_slots = level_six_slots;
    }

    public SpellSlots(int level_one_slots, int level_two_slots, int level_three_slots,
                      int level_four_slots, int level_five_slots) {

        this.level_one_slots = level_one_slots;
        this.level_two_slots = level_two_slots;
        this.level_three_slots = level_three_slots;
        this.level_four_slots = level_four_slots;
        this.level_five_slots = level_five_slots;
    }

    public SpellSlots(int level_one_slots, int level_two_slots, int level_three_slots, int level_four_slots) {

        this.level_one_slots = level_one_slots;
        this.level_two_slots = level_two_slots;
        this.level_three_slots = level_three_slots;
        this.level_four_slots = level_four_slots;
    }

    public SpellSlots(int level_one_slots, int level_two_slots, int level_three_slots) {

        this.level_one_slots = level_one_slots;
        this.level_two_slots = level_two_slots;
        this.level_three_slots = level_three_slots;
    }

    public SpellSlots(int level_one_slots, int level_two_slots) {

        this.level_one_slots = level_one_slots;
        this.level_two_slots = level_two_slots;
    }

    public int getLevel_one_slots() {
        return level_one_slots;
    }

    public void setLevel_one_slots(int level_one_slots) {
        this.level_one_slots = level_one_slots;
    }

    public int getLevel_two_slots() {
        return level_two_slots;
    }

    public void setLevel_two_slots(int level_two_slots) {
        this.level_two_slots = level_two_slots;
    }

    public int getLevel_three_slots() {
        return level_three_slots;
    }

    public void setLevel_three_slots(int level_three_slots) {
        this.level_three_slots = level_three_slots;
    }

    public int getLevel_four_slots() {
        return level_four_slots;
    }

    public void setLevel_four_slots(int level_four_slots) {
        this.level_four_slots = level_four_slots;
    }

    public int getLevel_five_slots() {
        return level_five_slots;
    }

    public void setLevel_five_slots(int level_five_slots) {
        this.level_five_slots = level_five_slots;
    }

    public int getLevel_six_slots() {
        return level_six_slots;
    }

    public void setLevel_six_slots(int level_six_slots) {
        this.level_six_slots = level_six_slots;
    }

    public int getLevel_seven_slots() {
        return level_seven_slots;
    }

    public void setLevel_seven_slots(int level_seven_slots) {
        this.level_seven_slots = level_seven_slots;
    }

    public int getLevel_eight_slots() {
        return level_eight_slots;
    }

    public void setLevel_eight_slots(int level_eight_slots) {
        this.level_eight_slots = level_eight_slots;
    }

    public int getLevel_nine_slots() {
        return level_nine_slots;
    }

    public void setLevel_nine_slots(int level_nine_slots) {
        this.level_nine_slots = level_nine_slots;
    }

    public String toString(){
        return String.format("%d, %d, %d, %d, %d, %d, %d, %d, %d",
                level_one_slots,
                level_two_slots,
                level_three_slots,
                level_four_slots,
                level_five_slots,
                level_six_slots,
                level_seven_slots,
                level_eight_slots,
                level_nine_slots);
    }
}
