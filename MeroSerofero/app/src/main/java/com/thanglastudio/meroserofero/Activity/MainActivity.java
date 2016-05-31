package com.thanglastudio.meroserofero.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.thanglastudio.meroserofero.Model.HealthNews;
import com.thanglastudio.meroserofero.Adapter.HealthNewsAdapter;
import com.thanglastudio.meroserofero.R;

public class MainActivity extends AppCompatActivity implements HealthNewsAdapter.Callback {

    HealthNewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);

        RecyclerView newsRecyclerview=(RecyclerView)findViewById(R.id.news_recyclerview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEditDialog(null);
            }
        });


        mAdapter=new HealthNewsAdapter(this,this);

        newsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerview.setHasFixedSize(true);
        newsRecyclerview.setAdapter(mAdapter);


//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                //Remove swiped item from list and notify the RecyclerView
//                Toast.makeText(MainActivity.this,"Swipe",Toast.LENGTH_SHORT).show();
//
//            }
//        };
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//
//        itemTouchHelper.attachToRecyclerView(newsRecyclerview);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id ==R.id.search) {
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onEdit(final HealthNews healthNews) {

        showAddEditDialog(healthNews);

    }

    @Override
    public void onItemClick(HealthNews healthNews) {
        Intent intent= new Intent(MainActivity.this,NewsDetailActivity.class);

        String title=healthNews.getNews_title();
        String content=healthNews.getNews_content();

        intent.putExtra("title",title);
        intent.putExtra("content",content);
        startActivity(intent);
    }

    private void showAddEditDialog(final HealthNews healthNews) {
        DialogFragment df = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(healthNews == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null, false);
                builder.setView(view);
                final EditText quoteEditText = (EditText) view.findViewById(R.id.dialog_news_title);
                final EditText movieEditText = (EditText) view.findViewById(R.id.dialog_news_content);
                if (healthNews != null) {
                    // pre-populate
                    quoteEditText.setText(healthNews.getNews_title());
                    movieEditText.setText(healthNews.getNews_content());

                    TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // empty
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            // empty
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            String quote = quoteEditText.getText().toString();
                            String movie = movieEditText.getText().toString();
                            mAdapter.update(healthNews, quote, movie);
                        }
                    };

                    quoteEditText.addTextChangedListener(textWatcher);
                    movieEditText.addTextChangedListener(textWatcher);
                }

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (healthNews == null) {
                            String quote = quoteEditText.getText().toString();
                            String movie = movieEditText.getText().toString();
                            mAdapter.add(new HealthNews(quote, movie));
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);

                return builder.create();
            }
        };
        df.show(getSupportFragmentManager(), "add");
    }
}
