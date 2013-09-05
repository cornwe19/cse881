A = load('user.txt');
[f,v] = hist( A(:,4), 10000 );
scatter(f,v);
set( gca, 'XScale', 'log' );
set( gca, 'YScale', 'log' );
