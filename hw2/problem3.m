trainX = load( 'data/trainX.data' );
trainY = load( 'data/trainY.data' );

trainXSparse = spconvert( trainX );
trainYCenter = trainY - mean( trainY );

lambda = 10000;

%b = ridge( trainY, trainXSparse, k );
b = inv( trainXSparse'*trainXSparse + lambda*eye(size(trainXSparse,2)) ) * trainXSparse' * trainYCenter;

testX = load( 'data/testX.data' );
testY = load( 'data/testY.data' );

testXSparse = spconvert( testX );
testYCenter = testY - mean( trainY );

disp( 'First 10 projections of test data' );
testXSparse( 1:10, : ) * b

disp( 'Actual test results' );
testYCenter( 1:10, : )
