package com.parking.parkinglot.ejb;

import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import com.parking.parkinglot.common.CarDto;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");

        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public CarDto findById(Long carId) {
        LOG.info("findById");
        Car car = entityManager.find(Car.class, carId);
        if (car != null) {
            String ownerName = (car.getOwner() != null) ? car.getOwner().getUsername() : "No Owner";
            return new CarDto(
                    car.getId(),
                    car.getLicense_plate(),
                    car.getParkingSpot(),
                    ownerName
            );
        }
        return null;
    }

    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar");
        Car car = new Car();
        car.setLicense_plate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);
        entityManager.persist(car);
    }

    public List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> dtos = new ArrayList<>();
        for (Car car : cars) {
            String ownerName = (car.getOwner() != null) ? car.getOwner().getUsername() : "No Owner";
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicense_plate(),
                    car.getParkingSpot(),
                    ownerName
            );
            dtos.add(carDto);
        }
        return dtos;
    }

    public void updateCar(Long id, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("updateCar");
        Car car = entityManager.find(Car.class, id);

        if (car != null) {
            car.setLicense_plate(licensePlate);
            car.setParkingSpot(parkingSpot);

            User newOwner = entityManager.find(User.class, userId);
            if (newOwner != null) {
                car.setOwner(newOwner);
            }
        }
    }

    public void deleteCar(Long id) {
        LOG.info("deleteCar");

        Car car = entityManager.find(Car.class, id);
        if (car != null) {
            entityManager.remove(car);
        }
    }
    public void deleteCarsByIds(List<Long> carIds) {
        LOG.info("deleteCarsByIds");

        for (Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);
            if (car != null) {
                User owner = car.getOwner();
                if (owner != null) {
                    owner.getCars().remove(car);
                }
                entityManager.remove(car);
            }
        }
    }
}