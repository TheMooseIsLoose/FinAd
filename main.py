import pandas as pd
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
from tensorflow.keras import layers
from tensorflow import keras as keras
from tensorflow.keras.layers.experimental import preprocessing
import seaborn as sns
# Make numpy values easier to read.
np.set_printoptions(precision=3, suppress=True)


def build_and_compile_model(norm):
    model = keras.Sequential([
      norm,
      layers.Dense(64, activation='relu'),
      layers.Dense(64, activation='relu'),
      layers.Dense(1)
    ])

    model.compile(loss='mean_absolute_error', optimizer=tf.keras.optimizers.Adam(0.001))
    return model


def plot_loss(history):
    plt.plot(history.history['loss'], label='loss')
    plt.plot(history.history['val_loss'], label='val_loss')
    plt.ylim([0, 10])
    plt.xlabel('Epoch')
    plt.ylabel('Error [MPG]')
    plt.legend()
    plt.grid(True)


def plot_cafe(x, y):
    plt.scatter(train_features['Restaurants_And_Cafes'], train_labels, label='Data')
    plt.plot(x, y, color='k', label='Predictions')
    plt.xlabel('Restaurants_And_Cafes')
    plt.ylabel('Balance')
    plt.legend()


if __name__ == '__main__':
    # Load the data
    csv_data = pd.read_csv(
        "data.csv", header=0, sep=";",
        names=["Polluted_investments", "Insurance", "Other", "Income", "Tourism", "Pets", "Unclassified",
               "Health", "Residence", "Wellbeing_And_Beauty", "Movement", "Restaurants_And_Cafes",
               "Food_GroceryStore", "Credit_Payments", "Culture_Entertainment", "Welfare", "Hobbies",
               "Children", "Shopping", "balance"])
    csv_data.head()
    csv_data.dropna()  # drop rows with unknown values

    # Split into train and test data
    train_dataset = csv_data.sample(frac=0.9, random_state=0)
    test_dataset = csv_data.drop(train_dataset.index)

    # Inspect the data
    sns.pairplot(train_dataset[["Polluted_investments", "Insurance", "Other", "Income", "balance"]],
                 diag_kind='kde')
    plt.show()
    # print(train_dataset.describe().transpose())

    # Split into features and labels for training
    train_features = train_dataset.copy()
    test_features = test_dataset.copy()

    train_labels = train_features.pop("balance")
    test_labels = test_features.pop("balance")

    # print(train_dataset.describe().transpose()[['mean', 'std']])  # big std-deviation

    # Normalize
    normalizer = preprocessing.Normalization()
    normalizer.adapt(np.array(train_features))
    # print(normalizer.mean.numpy())

    # --- LINEAR REGRESSION OF DATA ---
    # Normalize specific data for linear regression
    cafe = np.array(train_features['Restaurants_And_Cafes'])

    cafe_normalizer = preprocessing.Normalization(input_shape=[1, ])
    cafe_normalizer.adapt(cafe)

    # Build the model
    cafe_model = tf.keras.Sequential([
        cafe_normalizer,
        layers.Dense(units=1)
    ])

    # print(cafe_model.summary())

    result1 = cafe_model.predict(cafe[:10])
    # print(result1)

    cafe_model.compile(
        optimizer=tf.optimizers.Adam(learning_rate=0.1),
        loss='mean_absolute_error')

    history = cafe_model.fit(
        train_features['Restaurants_And_Cafes'], train_labels,
        epochs=100,
        # suppress logging
        verbose=0,
        # Calculate validation results on 10% of the training data
        validation_split=0.1)

    hist = pd.DataFrame(history.history)
    hist['epoch'] = history.epoch
    # print(hist.tail())

    plot_loss(history)
    plt.show()

    x = tf.linspace(0.0, 250, 251)
    y = cafe_model.predict(x)

    plot_cafe(x, y)
    plt.show()

    # --- ONE INPUT DNN ---
    dnn_cafe_model = build_and_compile_model(cafe_normalizer)
    # print(dnn_cafe_model.summary())

    history = dnn_cafe_model.fit(
        train_features['Restaurants_And_Cafes'], train_labels,
        validation_split=0.2,
        verbose=0, epochs=100)

    plot_loss(history)
    plt.show()

    x = tf.linspace(0.0, 250, 251)
    y = dnn_cafe_model.predict(x)
    plot_cafe(x, y)
    plt.show()

    # --- MULTI INPUT DNN ---    # the final result to use
    dnn_model = build_and_compile_model(normalizer)
    # print(dnn_model.summary())

    history = dnn_model.fit(
        train_features, train_labels,
        validation_split=0.2,
        verbose=0, epochs=100)

    plot_loss(history)
    plt.show()
