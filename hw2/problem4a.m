% -- With outliers -- 

trainX0 = [2 3 4 4 7 5 7 6 4 8 3 6 5 3 87]';
trainY0 = [3 3 5 5 5 6 6 5 3 6 2 4 4 2 14]';

figure;
hold on;
scatter( trainX0, trainY0 );

w0 = inv( trainX0' * trainX0 ) * trainX0' * trainY0;

x0 = 0:1:100;
y0 = w0*x0;

plot( y0 );

% -- Without outliers --

trainX1 = trainX0(1:end-1,:);
trainY1 = trainY0(1:end-1,:);

figure;
hold on;
scatter( trainX1, trainY1 );

w1 = inv( trainX1' * trainX1 ) * trainX1' * trainY1;

x1 = 0:1:10;
y1 = w1*x1;

plot( y1 );

