package Proiect.ProiectWeb.Service;

public class OnlineException extends RuntimeException {

    public enum ErrorCode {
        RESERVATION_NOT_FOUND,
        RESERVATION_WITH_SAME_NAME_ALREADY_EXISTS,
        GUEST_COULD_NOT_BE_SAVED,
        GUEST_COULD_NOT_BE_REMOVED,
        GUEST_WITH_SAME_PHONE_ALREADY_EXISTS,
        GUEST_NOT_FOUND,
        HOTEL_NOT_FOUND,
        HOTEL_IS_FULL,
        ROOM_NOT_FOUND,
        ROOM_IS_OCCUPIED,
        STATUSES_NOT_FOUND,
        STATUS_NOT_FOUND,
        RESERVATION_COULD_NOT_BE_SAVED,
        RESERVATION_COULD_NOT_BE_PROCESSED

    }

    private ErrorCode error;

    private OnlineException(ErrorCode error) {
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }

    public static OnlineException reservationNotFound() {
        return new OnlineException(ErrorCode.RESERVATION_NOT_FOUND);
    }

    public static OnlineException reservationWithSameNameAlreadyExists() {
        return new OnlineException(ErrorCode.RESERVATION_WITH_SAME_NAME_ALREADY_EXISTS);
    }

    public static OnlineException guestCouldNotBeSaved() {
        return new OnlineException(ErrorCode.GUEST_COULD_NOT_BE_SAVED);
    }

    public static OnlineException guestCouldNotBeRemoved() {
        return new OnlineException(ErrorCode.GUEST_COULD_NOT_BE_REMOVED);
    }

    public static OnlineException guestWithSamePhoneAlreadyExists() {
        return new OnlineException(ErrorCode.GUEST_WITH_SAME_PHONE_ALREADY_EXISTS);
    }

    public static OnlineException guestNotFound(){
        return new OnlineException(ErrorCode.GUEST_NOT_FOUND);
    }

    public static OnlineException hotelNotFound(){
        return new OnlineException(ErrorCode.HOTEL_NOT_FOUND);
    }

    public static OnlineException hotelIsFull(){
        return new OnlineException(ErrorCode.HOTEL_IS_FULL);
    }

    public static OnlineException roomNotFound(){
        return new OnlineException(ErrorCode.ROOM_NOT_FOUND);
    }

    public static OnlineException roomIsOccupied(){
        return new OnlineException(ErrorCode.ROOM_IS_OCCUPIED);
    }

    public static OnlineException statusesNotFound(){
        return new OnlineException(ErrorCode.STATUSES_NOT_FOUND);
    }

    public static OnlineException statusNotFound(){
        return new OnlineException(ErrorCode.STATUS_NOT_FOUND);
    }

    public static OnlineException reservationCouldNotBeSaved(){
        return new OnlineException(ErrorCode.RESERVATION_COULD_NOT_BE_SAVED);
    }

    public static OnlineException reservationCouldNotBeProcessed(){
        return new OnlineException(ErrorCode.RESERVATION_COULD_NOT_BE_PROCESSED);
    }
}